import tensorflow as tf
with tf.Session() as sess:
    print(sess.run(tf.cast(8.61923122, tf.int32)))