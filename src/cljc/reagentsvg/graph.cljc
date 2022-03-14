;; ReagentSVG -- MIT License.
;; No affiliation with Reagent.
;; FIXME rename project, as it is simply some svg graphs in hiccup format.
(ns reagentsvg.graph
  (:require
   [clojure.string :as string]))


;; # SVG line and bar charts.
;; Limitation - styling is not flexible.
;; TODO assign ids to every element so that styles can be fully customized.
;; Sample usage:
#_ (comment

;; sample barchart data
(def bar-data
  [{:label "max"
    :value 100}
   {:label "min"
    :value 20}
   {:label "med"
    :value 50}
   {:label "ave"
    :value 60}])

;; render bar graph svg (hiccup)
(bar-graph 
  {:title "Bar Chart"
   :width 300
   :height 300
   :style "svg {stroke: green;}" ;; or omit style to use default bar-graph-style
   :colors ["red" "blue" "teal"] ;; default is cornflowerblue
   :padding 5                    ;; default is 5
   :tags #{:vertical}}
  bar-data)

;; sample linechart data
(def line-data
  [{:label "max"
    :value 100}
   {:label "min"
    :value 20}
   {:label "med"
    :value 50}
   {:label "ave"
    :value 60}])

;; render line graph svg (hiccup)
(line-graph
  {:title "Line Chart"
   :width 300
   :height 300} ;; style can also be specified
  line-data)


;; sample xy data
(def xy-data
  [{:label "org"
    :hidden true
    :x 0
    :y 0}
   {:label "10,20"
    :x 10
    :y 20}
   {:label "20,80"
    :x 20
    :y 80}
   {:label "30,50"
    :x 30
    :y 50}
   {:label "40,40"
    :x 40
    :y 40}
   {:label "50,70"
    :x 50
    :y 70}
   {:label "max"
    :hidden true
    :x 100
    :y 100}])

;; render xy
(graph/xy-graph
    {:title "XY Chart"
     :width 300
     :height 300} ;; style can also be specified
    xy-data)
)

(def bar-graph-style
  "
.box {
fill: black;
}
g text {
fill: white;
}
.bar {
stroke: silver;
stroke-width: 3;
}
")

(def bar-color-default "cornflowerblue")

(defn wrap-style [style-string]
  [:style {:type "text/css" :scoped true} style-string])

(defn bar-graph-item [box i item]
  (let [{:keys [box-id box-height max-val thickness padding colors]} box
        {:keys [label value]} item
        fill (nth colors (rem i (count colors)) bar-color-default)
        bar-length (-> (float value) (* box-height) (/ max-val) (* 0.8))]
    ^{:key (str "bar-item-" box-id "." i)}
    [:svg {:x padding :y (+ padding (* i thickness))}
     [:rect {:class "bar" :fill fill :width bar-length :height thickness :rx 10}]
       [:text {:x (+ bar-length 10) :y 25 :dy "10pt" } label]]))

(defn bar-graph [config data]
  (let [{:keys [width height title style colors padding]
         :or {colors [bar-color-default] padding 5}} config
        vertical? (get-in config [:tags :vertical])
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
             :colors colors
             :padding padding}]
    [:div
     [:svg {:width width :height height}
      (wrap-style (or style bar-graph-style))
      [:title title]
      [:g box-rotate
       [:rect {:class "box" :width "100%" :height "100%"}]
       (map-indexed (partial bar-graph-item box) data)]]]))

(def line-graph-style
  "
.box {
fill: black;
}
g text {
fill: white;
}
.guide {
fill: none;
stroke: cyan;
stroke-width: 1;
stroke-dasharray: 2;
}
.area {
fill: darkslategray;
}
.line {
stroke: darkslateblue;
stroke-width: 3;
fill: none;
}
.dot {
stroke: silver;
stroke-width: 1;
fill: cyan;
}
")

(defn line-graph-point [box points-acc item]
  (let [{:keys [box-height max-val thickness padding]} box
        value (get item :value 0)
        n (count points-acc)
        x (+ padding (* n thickness))
        y (-> (float value) (* box-height) (/ max-val) (* 0.8) (int))]
    (conj points-acc [x (- box-height y)])))

(defn line-graph-item [box n item]
  (let [{:keys [box-id points]} box
        label (:label item)
        [x y] (get points n [0 0])]
    ^{:key (str "line-item-" box-id "." n)}
    [:g
     [:circle {:class "dot" :cx x :cy y :r 5}]
     [:text {:x (+ 10 x) :y (- y 5) :dy "10pt" } label]]))

(defn line-poly-points [box]
  (let [{:keys [points]} box]
    (->> (into [] (map (fn [[x y]] (str x " " y))) points)
         (string/join ","))))

(defn line-graph-stroke [poly-points]
  [:polyline {:class "line" :points poly-points}])

(defn line-graph-fill [box poly-points]
  (let [{:keys [padding box-height points n]} box
        start (str padding " " box-height)
        end (str (get-in points [(dec n) 0]) " " box-height)]
   [:polygon {:class "area" :points (str start "," poly-points "," end)}]))

(defn line-graph-guides [box]
  (let [{:keys [box-height points]} box
        ;; M to point
        ;; V to box-height
        segments (->> points
                      (map (fn [[x y]] (str "M " x " " y" V " box-height " ")))
                      (apply str))]
    [:path {:class "guide" :d segments}]))

(defn line-graph [config data]
  (let [{:keys [width height title style]} config
        n  (count data)
        max-val (apply max (map :value data))
        thickness (/ width (inc n))
        box-0 {:box-id (rand-int 1000000)
               :box-height height
               :thickness thickness
               :n n
               :max-val max-val
               :padding 5}
        points (reduce (partial line-graph-point box-0) [] data)
        box (assoc box-0 :points points)
        poly-points (line-poly-points box)]
    [:div
     [:svg {:width width :height height}
      (wrap-style (or style line-graph-style))
      [:title title]
      [:g
       [:rect {:class "box" :width "100%" :height "100%"}]
       (line-graph-fill box poly-points)
       (line-graph-guides box)
       (line-graph-stroke poly-points)
       (map-indexed (partial line-graph-item box) data)]]]))

(def xy-graph-style
  "
.box {
fill: black;
}
g text {
fill: white;
}
.dot {
stroke: silver;
stroke-width: 1;
fill: lime;
}
")

(defn xy-graph-point [box points-acc item]
  (let [{:keys [box-width box-height max-x max-y padding]} box
        {vx :x vy :y :or {vx 0 vy 0}} item
        x (-> (float vx) (* box-width) (/ max-x) (* 0.8) (int) (+ padding))
        y (-> (float vy) (* box-height) (/ max-y) (* 0.8) (int) (+ padding))]
    (conj points-acc [x y])))

(defn xy-graph-item [box n item]
  (when-not (:hidden item)
    (let [{:keys [box-id points]} box
          label (:label item)
          [x y] (get points n [0 0])]
      ^{:key (str "xy-item-" box-id "." n)}
      [:g
       [:circle {:class "dot" :cx x :cy y :r 3}]
       [:text {:x (+ 5 x) :y (- y 5) :dy "10pt" } label]])))

(defn xy-graph [config data]
  (let [{:keys [width height title style]} config
        max-x (apply max (map :x data))
        max-y (apply max (map :y data))
        box-0 {:box-id (rand-int 1000000)
               :box-width width
               :box-height height
               :max-x max-x
               :max-y max-y
               :padding 5}
        points (reduce (partial xy-graph-point box-0) [] data)
        box (assoc box-0 :points points)]
    [:div
     [:svg {:width width :height height}
      (wrap-style (or style xy-graph-style))
      [:title title]
      [:g
       [:rect {:class "box" :width "100%" :height "100%"}]
       (map-indexed (partial xy-graph-item box) data)]]]))

