import tensorflow as tf
weight = tf.Variable(0)
bias = tf.Variable(0)

saver = tf.train.Saver()
with tf.Session() as sess:
    saver.restore(sess, "c:/tensorflow/model.ckpt")
    print(sess.run(weight))
    
