(ns resume.routes
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [compojure.core :refer [ANY GET PUT POST DELETE routes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response]]))

(defn home-routes [endpoint]
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
   (resources "/")))
