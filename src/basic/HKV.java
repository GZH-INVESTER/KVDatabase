package basic;

import java.io.*;
import java.util.HashMap;

public class HKV {
	/* 一个什么都没考虑的KV存储,我太垃圾了 */

	// 创建 HashMap 对象 KV(key-value),用来存储键值对
	HashMap<Integer, String> KV = new HashMap<Integer, String>();
	// 创建 HashMap 对象 KI(key-interview),用来存储键的访问次数
	HashMap<Integer, Integer> KI = new HashMap<Integer, Integer>();

	// 键值对类
	public class pairKV {
		Integer myKey;
		String myValue;
	}

	public class pairKI {
		Integer myKey;
		Integer myTimes;
	}

	// 添加键值对
	public void add(Integer key, String value) {
		KV.put(key, value);
		KI.put(key, 0);
	}

	// 根据键修改值
	public void edit(Integer key, String newValue) {
		String oldValue = KV.get(key);
		KV.replace(key, oldValue, newValue);
	}

	// 删除键值对
	public void del(Integer key) {
		KV.remove(key);
		KI.remove(key);
	}

	// 根据键查询值
	public String find(Integer key) {
		String value = KV.get(key);
		int n = KI.get(key);
		KI.replace(key, n, n + 1);
		return value;
	}

	@SuppressWarnings("unchecked")

	// 将数据从文件读出
	public void upLoad(String[] args) {
		File fileValue = new File("../../dataValue.txt");
		File fileTimes = new File("../../dataTimes.txt");
		// 读
		try {
			FileInputStream fisValue = null, fisTimes = null;
			ObjectInputStream oisValue = null, oisTimes = null;
			fisValue = new FileInputStream(fileValue);
			oisValue = new ObjectInputStream(fisValue);
			fisTimes = new FileInputStream(fileTimes);
			oisTimes = new ObjectInputStream(fisTimes);
			KV = (HashMap<Integer, String>) oisValue.readObject();
			KI = (HashMap<Integer, Integer>) oisTimes.readObject();
			oisValue.close();
			oisTimes.close();
		} catch (IOException e) {
			System.out.print("Exception");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(KV);
	}

	// 将数据写入文件
	public void download(String[] args) {
		File fileValue = new File("../../dataValue.txt");
		File fileTimes = new File("../../dataTimes.txt");
		// 写
		try {
			FileOutputStream fosValue = null, fosTimes = null;
			ObjectOutputStream oosValue = null, oosTimes = null;
			fosValue = new FileOutputStream(fileValue);
			oosValue = new ObjectOutputStream(fosValue);
			fosTimes = new FileOutputStream(fileTimes);
			oosTimes = new ObjectOutputStream(fosTimes);
			oosValue.writeObject(KV);
			oosValue.flush();
			oosValue.close();
			oosTimes.writeObject(KI);
			oosTimes.flush();
			oosTimes.close();
		} catch (IOException e) {
			System.out.print("Exception");
		}
	}

	// 查询量第k大的键值对
	public String findK(int k) {
		String res, targetValue;
		int size, num, targetKey;
		size = KI.size();
		num = 0;// counter

		// key的数组和查询次数的数组
		int[] key = new int[size];
		int[] times = new int[size];

		// hashMap遍历
		for (HashMap.Entry<Integer, Integer> entry : KI.entrySet()) {
			key[num] = entry.getKey();
			times[num] = entry.getValue();
			num++;
		}

		// k值合法性判断
		if (k <= size && k >= 0) {
			// 得到times最大的Key
			targetKey = findKth(0, size - 1, key, times, k);
			// 根据key查value
			targetValue = KV.get(targetKey);
			// 直接返String很不好但我不想写对象了，对象都没有，写什么对象,之后一定会注意
			res = "查询量第" + k + "大的键值对问" + targetKey + ":" + targetValue;
		} else {
			res = "k值不合法或超过了键值对总数";
		}
		return res;
	}
	
	// 在两个函数findKth和partSort里array1表示key,array2表示times
	
	// 判断这次下标有没有到k的位置
	public int findKth(int left, int right, int[] array1, int[] array2, int k) {
		int m = partSort(left, right, array1, array2);
		if ((m - left) > (k - 1) && left < right) {
			return findKth(left, m - 1, array1, array2, k);
		} else if ((m - left) < (k - 1) && left < right) {
			return findKth(m + 1, right, array1, array2, k - m + left - 1);
		} else {
			return array1[m];
		}
	}

	// 快排思想（逆序），一次排序，分治法
	public int partSort(int left, int right, int[] array1, int[] array2) {
		int key1 = array1[left];
		int key2 = array2[left];
		while (left < right) {
			while (left < right && array2[right] <= key2) {
				right--;
			}
			array1[left] = array1[right];
			array2[left] = array2[right];
			
			while (left < right && array2[left] >= key2) {
				left++;
			}
			array1[left] = array1[right];
			array2[right] = array2[left];
			
		}
		array1[left] = key1;
		array2[left] = key2;
		return left;
	}
}
