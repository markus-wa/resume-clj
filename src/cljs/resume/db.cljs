(ns resume.db
  (:require [cljs.spec.alpha :as s]))

(s/def ::first string?)
(s/def ::last string?)

(s/def ::name (s/keys :req-un [::first
                                 ::last]))

(s/def ::position string?)
(s/def ::summary string?)

(s/def ::about (s/keys :req-un [::name
                                 ::position
                                 ::summary]))

(s/def ::company string?)
(s/def ::timeperiod string?)

(s/def ::experience (s/keys :req-un [::company
                                 ::position
                                 ::timeperiod]))

(s/def ::resume (s/keys :req-un [::about
                                 ::experience]))

(println (s/conform ::resume {:about {:name {:first "John" :last "Doe"}
                                    :position "Software Engineer"
                                      :summary "Lorem ipsum"}
                              :experience {:company "ACME Co."
                                           :position "Software Engineer"
                                           :timeperiod "since September 2019"}}))

(def default-db
  {:about {:name {:first "John" :last "Doe"}
                               :position "Software Engineer"
                               :summary "Lorem ipsum"}
                       :experience {:company "ACME Co."
                                    :position "Software Engineer"
                                    :timeperiod "since September 2019"}})
;; TODO improve schema validation
(if-not (s/valid? ::resume default-db)
        (println (s/explain ::resume default-db)))
