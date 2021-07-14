(ns reagentsvg.core
  (:require
   [reagent.core :as reagent]
   [reagent.dom :as rdom]
   ))

(def bar-style
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

(defn bar-graph-item [box n item]
  (let [{:keys [box-id box-height max-val thickness padding]} box
        {:keys [label value]} item
        bar-length (-> (float value) (* box-height) (/ max-val) (* 0.8))]
    ^{:key (str "bar-item-" box-id "." n)} 
    [:svg {:x padding :y (+ padding (* n thickness))}
     [:rect {:class "bar" :width bar-length :height thickness :rx 10}]
       [:text {:x (+ bar-length 10) :y 25 :dy "10pt" } label]
     ]))

(defn bar-graph [style data]
  (let [{:keys [width height title]} style
        vertical? (get-in style [:tags :vertical])
        [box-width box-height] (if vertical?
                                 [height width]
                                 [width height])
        box-rotate (if vertical?
                     {:transform (str "rotate(-90) translate(" (- box-width) ",0)")}
                     {})
        n  (count data)
        max-val (apply max (map :value data))
        thickness (/ box-height (inc n))
        box {:box-id (rand-int 1000000)
             :box-height box-height
             :thickness thickness
             :max-val max-val
             :padding 5}]
    
    
    [:div
     [:style {:type "text/css" :scoped true}
      "
.g {
fill: black;
}
g text {
fill: white;
}
.bar {
stroke: silver;
stroke-width: 3;
fill: cornflowerblue;
}
"]
     [:svg {:width width :height height}
      [:title title]
      [:g box-rotate
       [:rect {:class "g" :width "100%" :height "100%"}]
       (map-indexed (partial bar-graph-item box) data)
       ]]]))

(defn home-page []
  (fn []
    [:span.main
     [:h1 "Welcome to reagentsvg"]
     [:p "Vertical bar graph"]
     (bar-graph bar-style bar-data)
     [:p "Horizontal graph"]
     (bar-graph (dissoc bar-style :tags) bar-data)
     ]))


;; -------------------------
;; Initialize app

(defn init! []
  (rdom/render [home-page] (.getElementById js/document "app")))
