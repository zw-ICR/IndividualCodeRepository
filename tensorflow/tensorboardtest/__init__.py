import tensorflow as tf
# a = tf.placeholder(dtype=tf.float32,name="va")
a = tf.Variable(0,name="va")
b = tf.constant(3,dtype=tf.int32,name="cb")
add = tf.add(a, b,name="add")

init = tf.initialize_all_variables()

merged = tf.summary.merge_all()

with tf.Session() as sess:
    sess.run(init)
    
    for i in range(10):
        a = tf.assign(a,i)
        tf.summary.histogram("tensorboard/a",a)
        sess.run(merged,a)
        print(sess.run(add))
    
    writer = tf.summary.FileWriter(logdir="tensorboard",graph=sess.graph)