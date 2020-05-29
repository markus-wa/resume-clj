(ns resume.application
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [resume.components.server-info :as sys.srv-info]
            [system.components.endpoint :as sys.endpoint]
            [system.components.handler :as sys.handler]
            [system.components.middleware :as sys.mw]
            [system.components.jetty :as sys.jetty]
            [resume.config :as cfg]
            [resume.routes :as routes]))

(defn app-system [config]
  (component/system-map
   :routes     (sys.endpoint/new-endpoint routes/home-routes)
   :middleware (sys.mw/new-middleware {:middleware (:middleware config)})
   :handler    (-> (sys.handler/new-handler)
                   (component/using [:routes :middleware]))
   :http       (-> (sys.jetty/new-web-server (:http-port config))
                   (component/using [:handler]))
   :server-info (sys.srv-info/server-info (:http-port config))))

(defn -main [& _]
  (let [config (cfg/config)]
    (-> config
        app-system
        component/start)))
