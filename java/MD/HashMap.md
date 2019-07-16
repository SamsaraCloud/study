#### HashMap(jdk1.8)

hashmap 基于 数组 + 链表; 当元素个数超过阀值, 会由链表转为 TreeNode(红黑树)

**链表转树形, 当table 大于等于 64 并且 table中单个元素的链表结构长度达到8**

**在极端情况下: 当连续存储的元素的 hash 相同, 个数达到 11时, 也就是说 table 中只有一个元素, 但是链表长度达到 11, 此时链表也会转树形**

```java
// 极端情况测试使用数据
private static final String STR = "Lq9BH,MR8aH,MQXAg,MQXBH,MR9Ag,N1wAg,N1wBH,N2WaH,LpXBH,N38aH,N39BH,N39Ag,N2XBH,LowAg,LpXAg,LpWaH,LowBH,Lq9Ag,MPwAg,MPvaH,MPwBH";
```

**put()**

```java
	static final int hash(Object key) {
        int h;
      	// HashMap 可以添加 null 键值对, null,永远在 table 数组第一元素位
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

	public V put(K key, V value) {
  		return putVal(hash(key), key, value, false, true);
	}

	// 1. 寻找 bucket, hash 碰撞
	// 2. 扩容
	final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
          	//① 第一次添加值, 初始化话table 数组大小
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
          	
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
          	// 插入相同 key 时, 不会对table 进行元素添加操作, 会将已经存在的 key 对应的 value 返回
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
              	// 当链表转红黑树后
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                  	// 此处循环同一节点下各个链表中的元素
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                      	// 当链表中的元素达到 8 个时, 链表会向树形转换, 也就是table当前槽位的链表
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                  	// 当链表中元素个数小于 8, 当遍历到最后一个元素循环终止
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
      	// 当元素个数超过扩容阀值, 进行扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
      	// 说明本次插入的 key 为首次插入, 对应的 value 在 map 中为 null; 说明插入成功
        return null;
    }
	// 链表转树形
	final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; Node<K,V> e;
      	// 在链表转树形时, 需要table大小为64, 数组中至少有一个链表的大小为 8, 否则对 table 数组扩容
      	// 在极端请款下, table 数组的元素的 hash 都相等且个数为 11 时, 此时table大小为64, 链表也会转为树形
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
      	// tab[index = (n - 1) & hash] 第一个元素, 后面插入的元素都是上一个元素的 next 节点
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            do {
              	// 根据第一个插入并瞒足转树形条件的元素新建 TreeNode, 并将链表转为树形
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }
	////////////////////putTreeVal
	final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                                       int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            TreeNode<K,V> root = (parent != null) ? root() : this;
            for (TreeNode<K,V> p = root;;) {
                int dir, ph; K pk;
                if ((ph = p.hash) > h)
                    dir = -1;
                else if (ph < h)
                    dir = 1;
              	// hash 碰撞, 其实就是 hash 值相同的多个元素; java 中用链表的方式解决, 最终通过 equals 方法来区分不同的 key, 相对于 jdk 1.7, 在极端情况下,1000个元素 jdk1.7 可能需要比较1000次, jdk1.8 最多只需要比较500次(红黑树, 一半)
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                          (kc = comparableClassFor(k)) == null) ||
                         (dir = compareComparables(kc, k, pk)) == 0) {
                    if (!searched) {
                        TreeNode<K,V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                             (q = ch.find(h, k, kc)) != null) ||
                            ((ch = p.right) != null &&
                             (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }

                TreeNode<K,V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K,V> xpn = xp.next;
                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null)
                        ((TreeNode<K,V>)xpn).prev = x;
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }

```

**resize()**

```java
	// resize 方法可以分为两部分
	// 1. 第一次添加数据, 初始化 table 数组大小
	// 2. 扩容, 增长数组大小, 数据的复制
	final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
  		//① 原始数组大小, 第一次为 0.
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
  		//① 扩容阀值 初始值 0
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
              	// oldThr << n  === oldThr * 2ⁿ  oldThr >> n  === oldThr / 2ⁿ
              	// 每次数组和阀值增长都是 2 的倍数
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
          	//① 第一次添加值, 初始化大小, 数组大小 16
            newCap = DEFAULT_INITIAL_CAPACITY;
          	//① 扩容阀值 12
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
  		//① 扩容阀值 12 
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
  		//① table 初始化
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

