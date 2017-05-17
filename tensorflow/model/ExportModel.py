'''
导出模型
'''
import tensorflow as tf
weight = tf.Variable(2,dtype="float32",name="weight")
bias = tf.Variable(1,dtype="float32",name="bias")
x = tf.placeholder(tf.float32,name="x")
y = tf.add(tf.multiply(x, weight),bias,name="y")

init = tf.initialize_all_variables()

saver = tf.train.Saver()
with tf.Session() as sess:
    sess.run(init)
    print("y:",sess.run(y,feed_dict={x:5.0}))
    saver.save(sess, "../data/model.ckpt")