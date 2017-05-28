'''
使用tensorboard 显示执行的效果
'''
import tensorflow as tf
import numpy as np
from tensorflow.python.platform import flags
from sre_parse import FLAGS

#create data
x_data = np.random.rand(100).astype(np.float32)
y_data = x_data*0.1 + 0.3

#create tensorflow structure start
Weight = tf.Variable(tf.random_uniform([1],-1.0,1.0),name="Weight")
tf.summary.histogram("Weight", Weight)#记录Weidht到日志中，最终在tensorbaord histogram中显示

biases = tf.Variable(tf.zeros([1]),name="biases")
tf.summary.histogram("biases",biases)

y = Weight * x_data + biases

loss = tf.reduce_mean(tf.square(y-y_data))
tf.summary.scalar("losss", loss)#在tensorboard scalar视图打印数值走向

optimizer = tf.train.GradientDescentOptimizer(0.5)

train = optimizer.minimize(loss)

merged = tf.summary.merge_all()#合并所有的summary，important

init = tf.initialize_all_variables()
#create tensorflow structure end

file_wirter = tf.summary.FileWriter("tensorboard/")
with tf.Session() as sess:
    sess.run(init)
    file_wirter.add_graph(sess.graph)
    for i in range(201):
        sess.run(train)
        if i % 20 == 0:
            print(i,sess.run(Weight),sess.run(biases))
            summary = sess.run(merged)
            file_wirter.add_summary(summary,i)
    
