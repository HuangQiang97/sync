### Colab

```python
使用GPU TPU
Edit -> Notebook setting

# 验证使用GPU
%tensorflow_version 2.x
import tensorflow as tf
device_name = tf.test.gpu_device_name()
if device_name != '/device:GPU:0':
  raise SystemError('GPU device not found')
print('Found GPU at: {}'.format(device_name))

# 验证使用TPU
%tensorflow_version 2.x
import tensorflow as tf
print("Tensorflow version " + tf.__version__)
try:
  tpu = tf.distribute.cluster_resolver.TPUClusterResolver()  # TPU detection
  print('Running on TPU ', tpu.cluster_spec().as_dict()['worker'])
except ValueError:
  raise BaseException('ERROR: Not connected to a TPU runtime; please see the previous cell in this notebook for instructions!')
tf.config.experimental_connect_to_cluster(tpu)
tf.tpu.experimental.initialize_tpu_system(tpu)
tpu_strategy = tf.distribute.experimental.TPUStrategy(tpu)
```

```python
# 使用tensorflow 1.x
%tensorflow_version 1.x
import tensorflow
```

```python
from google.colab import widgets
# 带表头，第一行与第一列为表头
grid = widgets.Grid(2, 2, header_row=True, header_column=True)
# 往表格输入内容
with grid.output_to(0, 0):
  print("Bye grid")
```

```python
from google.colab import widgets
t = widgets.TabBar(["hi", "bye"])
# 向tabbar输入内容
with t.output_to(0):
  print("I am temporary")
  t.clear_tab()  # 清楚当前tabbar
  print("I am permanent")
t.clear_tab(1) # 清除指定tabbar
 
```

```shell
! cmd # 执行命令行命令
!pip install # 安装第三方包
!apt-get install # 安装系统软件
```

```python
from google.colab import files
# 上传文件，支持多选
# 生成一个字典，key为文件名，value为文件内容
uploaded = files.upload()
for fn in uploaded.keys():
    print(fn, length=len(uploaded[fn]))
 # 下载文件
files.download('fime_name')
```

```python
from google.colab import drive
drive.mount('/content/drive')
# 读文件,根路径：/content/drive/My Drive/
with open('/content/drive/My Drive/some_dir/some.txt', 'w') as f:
  f.write('Hello Google Drive!')
# 将文件写入drive并卸载
drive.flush_and_unmount()
```

```
ipynb文件保存在: My Drive/Colab Notebooks/
```

### kaggle

```
使用GPU TPU
sidebar-> acceleartor
```

```
数据集只读文件夹挂载在: /kaggle/input/datasetname/
工作目录可读可写挂载在: /kaggle/working/
```

```
! cmd # 执行命令行命令
!pip install # 安装第三方包
```



