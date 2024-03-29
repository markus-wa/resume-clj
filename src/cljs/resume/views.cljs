(ns resume.views
  (:require [re-frame.core :as rf]))

(defn title
  [{:keys [name] :as about}]
  [:div
   [:h1 (:first name) " " (:last name)]
   [:h2 (:position about)]
   [:p (:summary about)]])

(defn contact
  [contact]
  [:div
   [:p
    (:addressLine1 contact)
    [:br]
    (:addressLine2 contact)
    [:br]
    (:city contact)
    [:br]
    (:postcode contact)
    [:br]
    (:country contact)]
   [:p.bold
    (:phone contact)
    [:br]
    (let [{:keys [email]} contact]
      [:a {:href (str "mailto:" email)} email])]])

(defn experience-entry
  [exp]
  [:div.no-break
   [:div.no-break
     [:h4 (:company exp) ", "
      [:span.location (:location exp) " - "] ; TODO: long dash
      [:span.title-italic (:position exp)]
      [:br]
      [:span.time-period (:timeperiod exp)]]
     [:p (:description exp)]]
   [:div.no-break
     [:h5 "Tasks"]
     [:ul
      (for [task (:tasks exp)]
        [:li {:key task} task])]]
   [:div.no-break
     [:h5 "Accomplishments"]
     [:ul
      (for [acc (:accomplishments exp)]
        [:li {:key acc} acc])]]])

(defn experience-section
  [experience]
  [:div
   [:h3 "Experience"]
   (for [exp experience]
     [:div {:key (str (:company exp) (:position exp) (:timeperiod exp))}
      [experience-entry  exp]])])

(defn education-entry
  [edu]
  [:div
   [:h4 (:at edu) ", "
    [:span.location (str (:location edu) " - ")]
    [:span.title-italic (:degree edu)]
    [:br]
    [:span.time-period (:timeperiod edu)]]
   [:p (:description edu)]])

(defn education-section
  [education]
  [:div.no-break
   [:h3 "Education"]
   (for [edu education]
     [:div {:key (:degree edu)}
      [education-entry edu]])])

(defn project-entry
  [project]
  [:div
   [:h4 (:name project) " - "
    [:span.title-italic (:platform project)]
    [:br]
    [:span.link-subtitle
     (let [url (:url project)]
       [:a {:href url} url])]]
   [:p (:description project)]])

(defn projects-section
  [projects]
  [:div.no-break
   [:h3 "Projects"]
   (for [p projects]
     [:div {:key (:name p)}
      [project-entry p]])])

(defn contribution-entry
  [contribution]
  [:div
   [:h4 (:name contribution)
    [:span (:url contribution)]]
   [:p (:description contribution)]])

(defn contribution-section
  [contributions]
  [:div.no-break
   [:h3 "Contributions"]
   (for [c contributions]
     [contribution-entry c])])

(defn skills-section
  [skill-levels]
  [:div
   [:h3 "Skills"]
   (for [level skill-levels]
     [:div {:key (:level level)}
      [:span.bold (:level level) ":"]
      [:ul
       (for [skill (:entries level)]
         [:li {:key skill} skill])]])])

(defn certificates
  [certs]
  [:div.no-break
   [:h3 "Certificates"]
   (for [cert certs]
     (let [{:keys [issuer name]} cert]
       [:p {:key (str issuer name)} [:span.bold issuer] " - " name]))])

(defn languages
  [langs]
  [:div.no-break
   [:h3 "Languages"]
   (for [lang langs]
     (let [{:keys [name level]} lang]
       [:p {:key name} [:span.bold name] " - " level]))])

(defn main-panel
  []
  (let [resume @(rf/subscribe [:resume])]
    [:div
     [:div.flex-container
      [:div.flex-buffer]
      [:div.content
       [:div.main
        [title (:about resume)]]
       [:div.sidebar
        [:br]
        [contact (:contact (:about resume))]]]
      [:div.flex-buffer]]
     [:div.flex-container
      [:div.flex-buffer]
      [:div.content
       [:div.main
        [experience-section (:experience resume)]
        [education-section (:education resume)]
        [projects-section (:projects resume)]]
       [:div.sidebar
        [skills-section (:skills resume)]
        [certificates (:certificates resume)]
        [languages (:languages resume)]]]
      [:div.flex-buffer]]]))
