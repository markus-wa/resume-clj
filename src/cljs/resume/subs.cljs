(ns resume.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :resume
 (fn [db]
   (:resume db)))
