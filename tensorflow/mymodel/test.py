import tensorflow as tf
weight = tf.Variable(0)
bias = tf.Variable(0)

init = tf.initialize_all_variables()

saver = tf.train.Saver()
with tf.Session() as sess:
    sess.run(init)
    sess.run(tf.assign(weight,5))
    print(sess.run(weight))
    
    saver.save(sess,"c:/tensorflow/model.ckpt")
