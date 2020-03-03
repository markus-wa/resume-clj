(ns resume.views
  (:require [re-frame.core :as re-frame
             :refer            [subscribe]]))

(defn title
  []
  (let [about @(subscribe [:about])
        name  (:name about)]
    [:div
     [:h1 (:first name) " " (:last name)]
     [:h2 (:position about)]
     [:p (:summary about)]]))

(defn main-panel
  []
  (let [about @(subscribe [:about])
        name  (:name about)]
   [:div
     [:div "Hello from " (:first name)]
     [title]]))
