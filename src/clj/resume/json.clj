(ns resume.json
  (:require [clojure.java.io :as io]
            [cheshire.core :as json]))

(defn -main [& _]
  (->> "resume.edn"
       io/resource
       slurp
       read-string
       json/generate-string
       (spit "resources/public/resume.json")))
