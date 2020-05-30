(ns resume.style
  (:require [garden.core :refer [css]]
            [garden.stylesheet :refer [at-media]]))

(defmacro defs
  [& bindings]
  {:pre [(even? (count bindings))]}
  `(do
     ~@(for [[sym init] (partition 2 bindings)]
         `(def ~sym ~init))))

(defs
  base-font-size 1
  base-line-height 1.5
  header-line-height 1.25)

(defs
  bp-small "48em"
  bp-medium "64em"
  bp-large "85.375em"
  bp-xlarge "120em"
  bp-xxlarge "160em")

(defs
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
       (at-media mq-medium
                 [:body
                  {:font-size (em (* 1.2 base-font-size))
                   :line-height (* 1.2 base-line-height)}])
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
       [:a {:text-decoration "none"}]
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
       [:.title-italic
        {:font-weight "normal"
         :font-style "italic"}]
       [:.github-fork-ribbon
        [:&:before
        {:background-color "rgb(51, 51, 51)"}]]))
