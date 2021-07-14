(ns reagentsvg.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   [reagentsvg.graph :as graph]
   ))

(def bar-config
  {:title "Bar Chart"
   :width 300
   :height 300
   :tags #{:vertical}})

(def bar-data
  [{:label "max"
    :value 100}
   {:label "min"
    :value 20}
   {:label "med"
    :value 50}
   {:label "ave"
    :value 60}])

(def line-config
  {:title "Line Chart"
   :width 300
   :height 300})

(def line-data
  [{:label "max"
    :value 100}
   {:label "min"
    :value 20}
   {:label "med"
    :value 50}
   {:label "ave"
    :value 60}])

(defn home-page []
  (fn []
    [:span.main
     [:h1 "Welcome to reagentsvg"]
     [:p "Line graph"]
     (graph/line-graph line-config line-data)
     [:p "Vertical bar graph"]
     (graph/bar-graph bar-config bar-data)
     [:p "Horizontal graph"]
     (graph/bar-graph (dissoc bar-config :tags) bar-data)
     ]))


;; -------------------------
;; Initialize app

(defn init! []
  (rdom/render [home-page] (.getElementById js/document "app")))
