; not sure if any of this is useful, might be better to nuke the whole thing

((nil . ((cider-ns-refresh-before-fn . "reloaded.repl/suspend")
         (cider-ns-refresh-after-fn  . "reloaded.repl/resume")
         (cider-cljs-lein-repl    . "(do (user/go) (user/cljs-repl))"))))
