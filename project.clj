(defproject stash-bot "0.1.0-SNAPSHOT"
  :description "Raspberry PI based robot that responds to actions on Stash repos"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170" :classifier "aot"
                  :exclusion [org.clojure/data.json]]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.1"]]
  :npm {:dependencies [[source-map-support "0.3.2"
                        express "4.13.3"
                        body-parser "1.14.1"
                        johnny-five "0.9.2"
                        raspi-io "5.0.0"]]}

  :source-paths ["src" "target/classes"]
  :clean-targets ["out" "release" "main.js"]
  :target-path "target")
