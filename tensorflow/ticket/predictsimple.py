
import csv
import tensorflow as tf
csvfile = open("data/train/traindata.csv","r")
reader = csv.reader(csvfile)

y = tf.placeholder(dtype=tf.float32)
x = tf.placeholder(dtype=tf.float32)

Weight = tf.Variable(tf.random_uniform([1]),name="Weight")
biases = tf.Variable(tf.zeros([1]),name="biases")

y_predict = Weight * x + biases

loss = tf.reduce_mean(tf.square(y-tf.nn.relu(y_predict)))

# optimizer = tf.train.GradientDescentOptimizer(0.5)
optimizer = tf.train.AdamOptimizer(1e-4)

train = optimizer.minimize(loss)

init = tf.initialize_all_variables()
 
with tf.Session() as sess:
    sess.run(init)
    count = 1
    for line in reader:
#         print("x=",sess.run(x,feed_dict={x:line[0]}))
#         print("y=",sess.run(y,feed_dict={y:line[1]}))
        sess.run(train,feed_dict={x:line[0],y:line[1]})
#         print(count,sess.run(Weight),sess.run(biases))
        print(sess.run(loss,feed_dict={x:line[0],y:line[1]}))
        count = count + 1
    print("tttt")



