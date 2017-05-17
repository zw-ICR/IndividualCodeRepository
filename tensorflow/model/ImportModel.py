'''
导入模型
'''
import tensorflow as tf
saver = tf.train.import_meta_graph("../data/model.ckpt.meta")#加载图结构
with tf.Session() as sess:
    sess.run(tf.initialize_all_variables())
    
    graph = tf.get_default_graph()#load the graph
    
    x = graph.get_tensor_by_name("x:0")
    op = graph.get_tensor_by_name("y:0")
    
    print(sess.run(op,feed_dict={x:45}))