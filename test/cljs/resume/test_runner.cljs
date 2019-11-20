(ns resume.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [resume.core-test]
   [resume.common-test]))

(enable-console-print!)

(doo-tests 'resume.core-test
           'resume.common-test)
