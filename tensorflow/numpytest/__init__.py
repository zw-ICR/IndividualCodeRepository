import numpy as np
ary = np.array([[1,2],[1,2]],dtype=np.float32)
print(ary.shape)
#------------------------
ary = np.zeros((2,2),dtype=np.float32)
print(ary)
#----------------------------
ary = np.ones((3,3), dtype=np.int32)
print(ary.shape)
#-------------------------------
ary = np.full(2, fill_value =5)
print(ary)
#-------------------------------
ary = np.array([[1,2,3],[4,5,6]])
print((ary > 2))
#-------------------------
arry1 = np.array([[1,2,3],[4,5,6]])
print(np.reshape(arry1, (3,2)))
np.tan