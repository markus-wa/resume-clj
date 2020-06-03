(defproject resume "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773" :scope "provided"]
                 [org.clojure/tools.namespace "1.0.0"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [ring "1.8.1"]
                 [ring/ring-defaults "0.3.2"]
                 [bk/ring-gzip "0.3.0"]
                 [radicalzephyr/ring.middleware.logger "0.6.0"]
                 [clj-logging-config "1.9.12"]
                 [environ "1.2.0"]
                 [com.stuartsierra/component "1.0.0"]
                 [org.danielsz/system "0.4.5"]
                 [compojure "1.6.1"]
                 [re-frame "0.12.0"]
                 [day8.re-frame/http-fx "0.1.6"]
                 [ring/ring-json "0.5.0"]
                 [garden "1.3.10"]
                 [com.fasterxml.jackson.core/jackson-core "2.11.0"]]

  :plugins
  [[lein-cljsbuild "1.1.8"]
   [lein-environ "1.2.0"]]

  :min-lein-version "2.6.1"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  :test-paths ["test/clj" "test/cljc"]

  :clean-targets
  ^{:protect false} [:target-path
                     :compile-path
                     "resources/public/js"
                     "dev-target"]

  :uberjar-name "resume.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  :main resume.application

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (go) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild
  {:builds
   [{:id           "app"
     :source-paths ["src/cljs" "src/cljc" "dev"]

     :figwheel     {:on-jsload "resume.system/reset"}

     :compiler     {:main                 cljs.user
                    :asset-path           "js/compiled/out"
                    :output-to            "dev-target/public/js/compiled/resume.js"
                    :output-dir           "dev-target/public/js/compiled/out"
                    :source-map-timestamp true}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"]
     :compiler     {:output-to     "dev-target/public/js/compiled/testable.js"
                    :main          resume.test-runner
                    :optimizations :none}}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :jar          true
     :compiler     {:main                 resume.system
                    :output-to            "resources/public/js/compiled/resume.js"
                    :output-dir           "target"
                    :source-map-timestamp true
                    :optimizations        :advanced
                    :closure-defines      {goog.DEBUG false}
                    :pretty-print         false}}]}

  ;; When running figwheel from nREPL, figwheel will read this configuration
  ;; stanza, but it will read it without passing through leiningen's profile
  ;; merging. So don't put a :figwheel section under the :dev profile, it will
  ;; not be picked up, instead configure figwheel here on the top level.

  :figwheel
  {;; :http-server-root "public"       ;; serve static assets from resources/public/
  ;; :server-port 3449                ;; default
  ;; :server-ip "127.0.0.1"           ;; default
    ;:css-dirs       ["resources/public/css"]
    ;; watch and update CSS

    ;; Start an nREPL server into the running figwheel process. We
    ;; don't do this, instead we do the opposite, running figwheel from
    ;; an nREPL process, see
    ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
    ;; :nrepl-port 7888

    ;; To be able to open files in your editor from the heads up display
    ;; you will need to put a script on your path.
    ;; that script will have to take a file path and a line number
    ;; ie. in  ~/bin/myfile-opener
    ;; #! /bin/sh
    ;; emacsclient -n +$2 $1
    ;;
    ;; :open-file-command "myfile-opener"

    :server-logfile "log/figwheel.log"}

  :doo {:build "test"}

  :profiles
  {:dev
   {:dependencies [[figwheel "0.5.20"]
                   [figwheel-sidecar "0.5.20"]
                   [cider/piggieback "0.5.0"]
                   [cider/cider-nrepl "0.25.0"]
                   [lein-doo "0.1.11"]
                   [reloaded.repl "0.2.4"]]

    :plugins      [[lein-figwheel "0.5.20"]
                   [lein-doo "0.1.11"]
                   [lein-ancient "0.6.15"]]

    :source-paths ["dev"]
    :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}

   :uberjar
   {:source-paths ^:replace ["src/clj" "src/cljc"]
    :prep-tasks   ["compile"
                   ["cljsbuild" "once" "min"]]
    :hooks        []
    :omit-source  true
    :aot          :all}})
