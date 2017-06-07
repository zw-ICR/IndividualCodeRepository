import tensorflow as tf
a = tf.Variable(1,dtype=tf.float32)
init = tf.initialize_all_variables();
with tf.Session() as sess:
    sess.run(init);
    print(sess.run(a));
    
