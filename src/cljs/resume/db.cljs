(ns resume.db
  (:require [cljs.spec.alpha :as s]))

(s/def ::first string?)
(s/def ::last string?)

(s/def ::name
       (s/keys :req-un
               [::first
                ::last]))

(s/def ::position string?)
(s/def ::summary string?)

(s/def ::about
       (s/keys :req-un
               [::name
                 ::position
                 ::summary]))

(s/def ::company string?)
(s/def ::timeperiod string?)

(s/def ::experience
       (s/keys :req-un
               [::company
                 ::position
                 ::timeperiod]))

(s/def ::resume
       (s/keys :req-un
               [::about
                ::experience]))

(def default-db
  {:hello "world"})
