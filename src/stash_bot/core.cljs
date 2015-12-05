(ns stash-bot.core
  (:require [cljs.nodejs :refer [enable-util-print!]]
            [cljs.reader :refer [read-string]]))

(enable-util-print!)

;;------------------------------------------------------------------------------
;; node dependencies
;;------------------------------------------------------------------------------

(def fs (js/require "fs"))
(def path (js/require "path"))
(def http (js/require "http"))
(def express (js/require "express"))
(def body-parser (js/require "body-parser"))

;;------------------------------------------------------------------------------
;; atoms
;;------------------------------------------------------------------------------

(def config nil)
(def io-enabled? false)

;;------------------------------------------------------------------------------
;; general functionality
;;------------------------------------------------------------------------------

(def root-dir (.resolve path "."))
(def led nil)

(defn- read-config []
  (->> (.readFileSync fs (str root-dir "/config.clj"))
       (.toString)
       (read-string)))

(defn- turn-on-led []
  (println "Turn on LED")
  (when io-enabled?
    (.on led)))

(defn- turn-off-led []
  (println "Turn off LED")
  (when io-enabled?
    (.off led)))

(defn- blink-led []
  (println "Blink LED!")
  (when io-enabled?
    (turn-on-led)
    (js/setTimeout turn-off-led 5000))) ;; turn on LED for 5 seconds

(defn- remove-content-encoding
  "Stash sends an invalid content-encoding header. Remove it from the request object if detected"
  [req res next-fn]
  (let [headers (.-headers req)]
    (js-delete headers "content-encoding")
    (aset req "headers" headers)
    (next-fn)))

;;------------------------------------------------------------------------------
;; route handlers
;;------------------------------------------------------------------------------

(defn- index-handler [req res]
  (.sendFile res (str root-dir "/public/index.html")))

(defn- test-handler [req res]
  (let [led-state (-> req .-body .-ledState)]
    (if led-state
      (turn-on-led)
      (turn-off-led))
    (.sendStatus res 200)))

(defn- stash-handler [req res]
  (.sendStatus res 200) ;; Just respond with success immediately to stash
  (let [json-body (.-body req)
        ref-changes (.-refChanges json-body)]
    (when (array? ref-changes)
      (let [ref-change (aget ref-changes 0)
            ref-id (.-refId ref-change)
            type (.-type ref-change)
            update-to-master? (and (= ref-id "refs/heads/master")
                                   (= type "UPDATE"))]
        (when update-to-master?
          (blink-led)))))) ;; TODO: control motor and ring bell here

(defn- not-found [req res]
  (.sendStatus res 404))

;;------------------------------------------------------------------------------
;; server initialization
;;------------------------------------------------------------------------------

(defn- init-hardware [next-fn]
  (let [five (js/require "johnny-five")
        Raspi (js/require "raspi-io")
        board (js/five.Board. (js-obj "io" (Raspi.)))]
    (.on board "ready"
      (fn []
        (set! led (js/five.Led. "GPIO16"))
        (next-fn)))))

(defn- init-web-server []
  ; configure and start the server
  (let [app (express)
        static-file-handler (aget express "static")
        one-year (* 365 24 60 60 1000)
        static-opts (js-obj "maxAge" one-year)
        port (:port config)]
    (doto app
      (.get "/" index-handler)
      (.use (static-file-handler (str root-dir "/public") static-opts))
      (.use remove-content-encoding)
      (.use (.json body-parser))
      (.post "/test" test-handler)
      (.post "/stash-update" stash-handler)
      (.use not-found)
      (.listen port))
    (println (str "Listening on port " port))))

(defn -main [& args]
  (when-not (.existsSync fs (str root-dir "/config.clj"))
    (println "ERROR: config.clj not found")
    (println "HINT: Use example_config.clj to start a new one.")
    (.exit js/process 1))

  (set! config (read-config))
  (set! io-enabled? (:io-enabled? config))

  (if io-enabled?
    (init-hardware init-web-server)
    (init-web-server)))

(set! *main-cli-fn* -main)
