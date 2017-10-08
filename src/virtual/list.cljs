(ns virtual.list
  (:require [rum.core :as rum]
            [goog.object :as gobj]))

(def id* (atom nil))

(defn- handle-id [state timeout]
  (when-let [id @id*]
    (js/clearTimeout id))
  (reset! id* (js/setTimeout
                #(do
                   (reset! id* nil)
                   (swap! state assoc :scrolling? false))
                timeout)))

(rum/defcs v-list <
  rum/static
  (rum/local
    {:offset     0
     :scrolling? false}
    ::state)
  [{state ::state :as st}
   {:keys [rows-count
           max-rows-count
           row-height
           render-row
           overscan-count
           timeout]}]
  (let [{:keys [offset scrolling?]} @state
        start (int (/ offset row-height))
        start (if (pos? overscan-count)
                (->> (mod start overscan-count) (- start) (Math/max 0))
                start)
        height (* row-height rows-count)
        visible-row-count (int (/ height row-height))
        visible-row-count (if (pos? overscan-count)
                            (+ visible-row-count overscan-count)
                            visible-row-count)
        end (+ start visible-row-count)
        end (if (> end max-rows-count)
              max-rows-count
              end)
        handle-scroll #(do
                         (handle-id state (or timeout 150))
                         (swap! state
                                assoc
                                :offset (-> st :rum/react-component .-base (gobj/get "scrollTop"))
                                :scrolling? true))]

       [:div {:on-scroll handle-scroll
              :style     {:height     height
                          :overflow-y "auto"}}
        [:div
         {:style {:position   "relative"
                  :overflow   "hidden"
                  :min-height "100%"
                  :width      "100%"
                  :height     (* row-height max-rows-count)}}
         [:div
          {:style {:position    "absolute"
                   :will-change "transform"
                   :transform   (str "translate3d(0, " (* start row-height) "px, 0)")
                   :left        0
                   :height      "100%"
                   :width       "100%"
                   :overflow    "visible"}}
          (loop [idx start
                 out (array)]
                (if (< idx end)
                  (do
                    (.push out (render-row {:idx idx :scrolling? scrolling?}))
                    (recur (inc idx) out))
                  out))]]]))
