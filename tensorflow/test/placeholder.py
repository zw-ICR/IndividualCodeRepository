import tensorflow as tf
p1 = tf.placeholder(tf.float32)
p2 = tf.placeholder(tf.float32)

mul1 = tf.multiply(p1,p2)

with tf.Session() as sess:
    print(sess.run(mul1,feed_dict={p1:4,p2:6}))