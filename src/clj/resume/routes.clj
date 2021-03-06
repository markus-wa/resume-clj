(ns resume.routes
  (:require [clojure.java.io :as io]
            [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response]]
            [resume.style :as style]))

(defn home-routes
  [_endpoint]
  (routes
   (GET "/" _
        (-> "public/index.html"
            io/resource
            io/input-stream
            response
            (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
   (GET "/resume.json" _
        (-> "resume.edn"
            io/resource
            slurp
            read-string
            response
            (assoc :headers {"Content-Type" "application/json; charset=utf-8"})))
   (GET "/css/compiled/style.css" _
        (-> (style/build)
            response
            (assoc :headers {"Content-Type" "text/css; charset=utf-8"})))
   (resources "/")))
