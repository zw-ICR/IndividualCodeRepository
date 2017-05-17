import tensorflow as tf
import input_data
constant_1 = tf.constant([[1,2,3],[4,5,6]])
constant_2 = tf.constant([[1,4],[2,5],[3,6]])
result = tf.matmul(constant_1,constant_2)
with tf.Session() as sess:
    print(sess.run(result))
