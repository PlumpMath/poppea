(ns poppea.core-test
  (:use clojure.test
        poppea
        [clojure.string :as s]))

(def a 4)
(def b 5)

(deftest quick-map-test
  (is (= {:a 4 :b 5 :c 3}
         (coffee-map a b :c 3))))

(defn-curried add [x y] (+ x y))

(deftest currying
  (is (= 7
         ((add 3) 4))))

(defn f [a b] (a b))

(deftest document-partials
  (is (= count (:a (let [c count] (document-partial f c))))
      "Binds correctly to local bindings.")
  (is (document-partial string?)
      "Should handle partials with no parameters.")
  (is (= 3 ((document-partial count) [:a :b :c])))
  (is (= 5 ((document-partial + 2 1) 2)))
  (is (= 3 ((document-partial-% count) [:a :b :c])))
  (is (= 5 ((document-partial-% + 2 1) 2)))
  (is ((document-partial-% < % 4) 3))
  (is (= [2 7 3 4]
         ((document-partial-% list %2 7) 1 2 3 4)))
  (is (not ((document-partial-% < % 4) 4))))

(deftest symbol-resolution
  (is (document-partial s/blank?)
      "Should handle aliases."))
