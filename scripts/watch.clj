(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'stash-bot.core
   :output-to "main.js"
   :target :nodejs
   :verbose true})
