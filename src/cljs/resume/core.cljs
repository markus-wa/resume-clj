(ns resume.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [resume.events]
            [resume.subs]
            [resume.views :as views]
            [resume.config :as config]
            [day8.re-frame.http-fx]))

(enable-console-print!)

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn render []
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch-sync [:get-resume])
  (dev-setup)
  (mount-root))
