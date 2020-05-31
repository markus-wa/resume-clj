(ns resume.style
  (:require clojure.java.io
            [garden.core :refer [css]]
            [garden.stylesheet :refer [at-media]]))

(defmacro defs
  [& bindings]
  {:pre [(even? (count bindings))]}
  `(do
     ~@(for [[sym init] (partition 2 bindings)]
         `(def ~sym ~init))))

(declare
 base-font-size
 base-line-height
 header-line-height
 bp-xsmall
 bp-small
 bp-medium
 bp-large
 bp-xlarge
 bp-xxlarge
 mq-xsmall
 mq-small
 mq-medium
 mq-large
 mq-xlarge
 mq-xxlarge
 mq-retina)

(defs
  base-font-size 1
  base-line-height 1.5
  header-line-height 1.25)

(defs
  bp-xsmall "32em"
  bp-small "48em"
  bp-medium "64em"
  bp-large "85.375em"
  bp-xlarge "120em"
  bp-xxlarge "160em")

(defs
  mq-xsmall {:min-width bp-xsmall}
  mq-small {:min-width bp-small}
  mq-medium {:min-width bp-medium}
  mq-large {:min-width bp-large}
  mq-xlarge {:min-width bp-xlarge}
  mq-xxlarge {:min-width bp-xxlarge}
  mq-retina {:-webkit-min-device-pixel-ratio 2
             :min-resolution "192dpi"})

(defn- em
  [size]
  (str size "em"))

(defn build
  []
  (css [:body
        {:font-size (em base-font-size)
         :line-height (str base-line-height)}]
       (at-media mq-xsmall
                 [:.content
                  {:display "block"}])
       (at-media mq-medium
                 [:body
                  {:font-size (em (* 1.2 base-font-size))
                   :line-height (* 1.2 base-line-height)}])
       (at-media mq-small
                 [:.content
                  {:display "flex"
                   :flex 3}])
       (at-media mq-large
                 [:body
                  {:font-size (em (* 1.3 base-font-size))}])
       (at-media mq-xlarge
                 [:body
                  {:font-size (em (* 1.4 base-font-size))}])
       (at-media mq-xxlarge
                 [:body
                  {:font-size (em (* 1.6 base-font-size))}])
       [:*
        {:font-family "monospace"}]
       [:h3
        {:text-transform "uppercase"
         :color "CornflowerBlue"}]
       [:.flex-container
        {:display "flex"}]
       (at-media mq-large
                 [:.flex-buffer
                  {:flex 1}])
       [:.main
        {:flex 2
         :margin "0px 10px"}]
       [:.sidebar
        {:flex 1
         :margin "0px 10px"}]
       [:.bold
        {:font-weight "bold"}]
       [:.location
        {:font-weight "normal"}]
       [:.subtitle
        {:font-size "smaller"}]
       [:.time-period
        {:font-weight "normal"
         :font-size "smaller"
         :text-transform "uppercase"
         :color "Gray"}]
       [:a :a:visited :a:hover :a:active
        {:color "inherit"}]
       [:.link-subtitle
        {:font-weight "normal"
         :font-size "smaller"
         :color "Gray"}]
       [:.title-italic
        {:font-weight "normal"
         :font-style "italic"}]
       [:.github-fork-ribbon
        [:&:before
         {:background-color "rgb(51, 51, 51)"}]]))

(defn -main [& _]
  (let [file "resources/public/css/compiled/style.css"]
    (clojure.java.io/make-parents file)
    (spit file (build))))
