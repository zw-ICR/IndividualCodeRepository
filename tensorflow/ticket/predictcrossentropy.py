'''
Created on 2017年6月01日
交叉熵方式计算loss
@author: zw
'''
import tensorflow as tf
import numpy as np
import csv

def add_layer(inputs,in_size,out_size,activation_function=None):
    Weight = tf.Variable(tf.random_normal([in_size,out_size]))
    biases = tf.Variable(tf.zeros([1,out_size]) + 0.1)
    Wx_plus_b = tf.matmul(inputs,Weight) + biases
    if activation_function is None:
        return Wx_plus_b
    
    return activation_function(Wx_plus_b)

def readFile(filePath):
    xs = []
    ys = []
    
    csvfile = open(filePath,"r")
    reader = csv.reader(csvfile)
    for line in reader:
#         print("x=%s’,y=%s‘",line[0],line[1])
        xs.append([int(line[0])])
        ys.append([int(line[1])])
    
    
    return xs,ys
        

# for test
# x_data = np.linspace(-1,1,300)[:,np.newaxis]
# noise = np.random.normal(0,0.05,x_data.shape)
# y_data = np.square(x_data) - 0.5 + noise

xs = tf.placeholder(dtype=tf.float32,shape = [None,1])
ys = tf.placeholder(dtype=tf.float32,shape = [None,1])

x_data,y_data = readFile("data/train/traindata.csv")

hidden_layer = add_layer(xs,1,16,activation_function=tf.nn.relu6)
prediction_layer = add_layer(hidden_layer, 16, 1, activation_function = None)

loss = tf.reduce_mean(tf.reduce_sum(tf.square(ys - prediction_layer),reduction_indices=[1]))

train_step = tf.train.GradientDescentOptimizer(0.01).minimize(loss)
    # train_step = tf.train.AdamOptimizer(1e-4).minimize(loss)

init = tf.initialize_all_variables()

file_wirter = tf.summary.FileWriter("tensorboard/")

with tf.Session() as sess:
    sess.run(init)
#     print(x_data)
    
    for i in range(1000):
        sess.run(train_step,feed_dict={xs:x_data,ys:y_data})
        if i % 100 == 0:
            sess.run(loss,feed_dict={xs:x_data,ys:y_data})
            
#         correct_prediction = tf.equal(ys,tf.cast(prediction_layer,tf.int32))
#         print(sess.run(ys,feed_dict={ys:y_data}))
        print(sess.run(tf.cast(prediction_layer,tf.int32),feed_dict={xs:x_data,ys:y_data}))
#         accuracy = tf.reduce_mean(tf.cast(correct_prediction,"float"))
#         print(sess.run(accuracy,feed_dict={xs:x_data,ys:y_data}))
    

                   
