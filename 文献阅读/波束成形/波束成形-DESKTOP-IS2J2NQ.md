### Grid-Free MIMO Beam Alignment through Site-Specific Deep Learning

1，直接从连续搜索空间合成发射（Tx）和接收（Rx）波束，使用通过深度学习（DL）管道找到的几个特定地点的探测波束的测量结果。链接的最佳波束对是传播环境的拓扑结构以及BS和UE的位置的直观函数。通过查找表或机器学习（ML）模型构建从CI到最佳波束索引的映射，可以大大减少搜索空间。

2，为了确保在任何地点的覆盖，量化的波束通常在角度空间中均匀地分配能量，如DFT码本。穷举式搜索的明显缺点是它的扫波延迟，它与波束对的总数呈线性增长。随着系统在频谱上的移动和采用更窄的波束，编码本的大小也相应增加。

在特定的环境拓扑结构中，以及有几个空间集群的UE时，统一编码本中的一些波束可能永远不会被使用。利用这一事实，BS可以在详尽的训练阶段后计算整个密码本的统计数据，并优先考虑最频繁使用的波束。

3，贡献：

* 所提出的方法合成来自连续搜索空间的模拟波束，而不是从量化码本中进行选择。特定站点探测波束允许BS和UE以较少的测量来捕获关键信道信息，波束合成器功能可以直观地视为无限大的码本。
* 该方法只有一次统一的扫描，无用户单独扫描或者补测，因此波束扫描延迟不会随着UE数量的增加而增加。

4，输入：双方各有一个概率码本用于扫描环境$F \in C^{N_T \times N_F},W \in C^{N_R \times N_W}$，此处让$N_F=N_W=N_{prob}$，得到扫描结果$    \mathbf{Y} = \sqrt{P_T}\mathbf{W}^{H}\mathbf{H}\mathbf{F}\mathbf{s}+\mathbf{W}^{H}\mathbf{n}, \in C^{N_W \times N_F}$，扫描波束双方同步变化，实际只扫描中对角线对应码字对，取得反馈结果$\mathrm{Z}=\bigg[|\big[\mathrm{diag}({\bf Y})\big]_{1}|^{2}\ \ \ldots\cdot\ \big|[\mathrm{diag}({\bf Y})]_{N_{\mathrm{probe}}}|^{2}\bigg]^{T}$。s

5，效用函数：
$$
\begin{align} \label{bf_loss}
\mathcal{U} &=  
\begin{cases}
\mathcal{U}_{\textnormal{BF}} & \text{if } \mathcal{H}_{\textnormal{IA}} = \mathcal{H} \\
\gamma\mathcal{U}_{\textnormal{BF}} + (1-\gamma)\mathcal{U}_{\textnormal{IA}} & \text{otherwise} \\
\end{cases} \\
\mathcal{U}_{\textnormal{BF}} &= \mathop{\mathbb{E}}\limits_{\mathbf{H} \in \mathcal{H}} \left[ \frac{|\mathbf{v}_\mathrm{R}^H \mathbf{H} \mathbf{v}_\mathrm{T}|^2}{||\mathbf{H}||^2_{\text{F}}} \right] \\
\mathcal{U}_{\textnormal{IA}} &= \mathop{\mathbb{E}}\limits_{\mathbf{H} \in \mathcal{H} \setminus \mathcal{H}_{\textnormal{IA}}} \left[ \max\limits_{\mathbf{f} \in \mathcal{F}, \mathbf{w} \in \mathcal{W}} \frac{|\mathbf{w}^H \mathbf{H} \mathbf{f}|^2}{||\mathbf{H}||^2_{\text{F}}} \right] \\
\mathcal{H}_{\textnormal{IA}} &= \{\mathbf{H} \in \mathcal{H}: \max\limits_{\mathbf{f} \in \mathcal{F}, \mathbf{w} \in \mathcal{W}} \frac{P_T|\mathbf{w}^H \mathbf{H} \mathbf{f}|^2}{|\mathbf{w}^H \mathbf{n}|^2} \geq \textnormal{SNR}_{\textnormal{TH}} \}
\end{align}
$$
其中$\mathrm{f},\mathrm{v}$表示网络输出的合成波束，$\mathcal{F},\mathcal{W}$表示概率探测波束。

公式(1)中第一项表示信道环境都较好，损失函数只和合成波束相关，第二项表示存在信道环境较差信道，损失函数和探测波束、合成波束相关，参数$\gamma$用于平衡合成波束和探测波束的增益之间的权衡。。

公式(2)表示使用网络输出波束时信道集合中最大增益的均值。为了向所有UE提供更好的覆盖，将由信道范数归一化的平均BF增益最大化，以便即使对于信道较差的UE也给予同等的重视。

公式(3)表示较差信道集合中使用探测波束对时的最大增益的均值。

公式(4)表示存在探测波束对使得增益高于阈值的信道集合，即环境较好信道。

<img src="%E6%B3%A2%E6%9D%9F%E6%88%90%E5%BD%A2.assets/image-20230114194529059.png" alt="image-20230114194529059" style="zoom:67%;" />

6，新UE需要满足最小SNR要求，以便它们可以在IA过程中被发现并连接到BS。UE的误检测概率，即UE使用最强探测波束对达到低于阈值SNR的概率：$ \textnormal{misdetection probability} = \mathop{\mathbb{E}}\limits_{\mathbf{H} \in \mathcal{H}} \left[ \mathrm{1}_{\mathcal{H}_{\textnormal{IA}}} (\mathbf{H})\right].   $，即存在探测波束对使得增益高于阈值的信道在全部信道集合中的占比。

7，随着探测波束对的数量增加，学习的探测波束具有更大的空间覆盖，利用探测波束收集的更多信息，波束合成器也学会了更准确和更精确地聚焦能量。

８，随机的UE旋转增加了AOA的有效范围。因此需要更多的探测波束来捕获UE侧的足够的信道信息。UE的探测波束还需要分配能量并覆盖更大的角度空间，从而导致BF增益降低和更差的误检概率。

９，与简单地优化合成波束的BF增益($\gamma $=1.0)相比，减小$\gamma$实现增益损失很小的情况下，降低误检率。

10，调参：天线形状、移相器架构、数字+模拟波束成形、激活基站、增益PDF 分布、概率码本大小、扫描开销VS 增益、固定训练SNR VS 在SNR单独训练、训练与测试H由于噪声不匹配、反馈$Z$取top-k其余置零、UE天线随随机旋转VS天线倾角固定、$\gamma$、接入增益阈值$\textnormal{SNR}_{\textnormal{TH}}$。

### Constrained Deep Neural Network based Hybrid Beamforming for Millimeter Wave Massive MIMO Systems

1，将混合波束形成系统建立为基于稀疏自动编码器的端到端深度神经网络训练模型，约束DNN是以无监督的方式训练，训练目标是重构输出作为输入。将传统的约束混合波束形成优化问题转化为神经网络优化问题。

2，稀疏自动编码器的目标是学习一组数据的表示，通常用于降维。训练有素的自动编码器能够通过学习数据中的相关性来创建隐藏层中数据的压缩表示。

### End-to-End Fast Training of Communication Links Without a Channel Model via Online Meta-Learning

1，先前的工作考虑了多个信道上的联合训练，目的是找到一对在一类信道上工作良好的编码器和解码器。建议通过元学习消除联合训练的局限性。

2，在实践中，可以通过修改发射机和接收机的位置或通过改变传播环境。

3，接收机元训练解码器以基于每个帧中有效载荷之前的导频快速适应新的信道条件；而发射器同时训练单个编码器。同时进行编码器和解码器的训练，使联合训练的编码器能够根据导频选择适合于当前信道的“相干”解码器的码本。
