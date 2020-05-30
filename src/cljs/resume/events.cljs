(ns resume.events
  (:require [re-frame.core :as rf]
            [resume.db :as db]
            [ajax.core :as ajax]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-fx
 :get-resume
 (fn [{:keys [db]} [_ a]]
   {:http-xhrio {:method     :get
                 :uri        "http://localhost:10555/resume.json"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:process-resume-response]
                 :on-fail    [:failed-get-reusme]}
    :db         (assoc db :loading? true)}))

(rf/reg-event-db
 :process-resume-response
 (fn
   [db [_ response]]
   (-> db
       (assoc :loading? false)
       (assoc :resume response))))
