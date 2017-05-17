import tensorflow as tf
v = tf.Variable(0)
c = tf.constant(1)

ret = tf.add(v,c)
op = tf.assign(v, ret)

init = tf.initialize_all_variables()
with tf.Session() as sess:
    sess.run(init)
    for _ in range(3):
        sess.run(op)
        print(sess.run(v))
    