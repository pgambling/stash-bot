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

;;------------------------------------------------------------------------------
;; atoms
;;------------------------------------------------------------------------------

(def config nil)

;;------------------------------------------------------------------------------
;; general functionality
;;------------------------------------------------------------------------------

(def root-dir (.resolve path "."))
(def led nil)

(defn- read-config []
  (->> (.readFileSync fs (str root-dir "/config.clj"))
       (.toString)
       (read-string)))

(defn- blink-led []
  (println "Blink LED!")
  (when (:enable-io config)
    (println "TODO: Hit IO"))) ;; do actual IO here

;;------------------------------------------------------------------------------
;; route handlers
;;------------------------------------------------------------------------------

(defn- index-handler [req res]
  (.sendFile res (str root-dir "/public/index.html")))

(defn- test-handler [req res]
  (blink-led)
  (.sendStatus res 200))

(defn- not-found [req res]
  (.sendStatus res 404))

;;------------------------------------------------------------------------------
;; server initialization
;;------------------------------------------------------------------------------

(defn- init-hardware [next-fn]
  (let [five (js/require "johnny-five")
        raspi-io (js/require "raspi-io")
        board (js/five.Board. (js-obj "io" raspi-io))]
    (.on board "ready"
      (fn []
        (set! led (js/five.Led. "P1-13"))
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
      (.post "/test" test-handler)
      (.use (static-file-handler (str root-dir "/public") static-opts))
      (.use not-found)
      (.listen port))
    (println (str "Listening on port " port))))

(defn -main [& args]
  (when-not (.existsSync fs (str root-dir "/config.clj"))
    (println "ERROR: config.clj not found")
    (println "HINT: Use example_config.clj to start a new one.")
    (.exit js/process 1))

  (set! config (read-config))

  (if (:enable-io config)
    (init-hardware init-web-server)
    (init-web-server)))



(set! *main-cli-fn* -main)
