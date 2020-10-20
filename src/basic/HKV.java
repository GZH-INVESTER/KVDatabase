package basic;

import java.io.*;
import java.util.HashMap;

public class HKV {
	/* һ��ʲô��û���ǵ�KV�洢,��̫������ */

	// ���� HashMap ���� KV(key-value),�����洢��ֵ��
	HashMap<Integer, String> KV = new HashMap<Integer, String>();
	// ���� HashMap ���� KI(key-interview),�����洢���ķ��ʴ���
	HashMap<Integer, Integer> KI = new HashMap<Integer, Integer>();

	// ��ֵ����
	public class pairKV {
		Integer myKey;
		String myValue;
	}

	public class pairKI {
		Integer myKey;
		Integer myTimes;
	}

	// ��Ӽ�ֵ��
	public void add(Integer key, String value) {
		KV.put(key, value);
		KI.put(key, 0);
	}

	// ���ݼ��޸�ֵ
	public void edit(Integer key, String newValue) {
		String oldValue = KV.get(key);
		KV.replace(key, oldValue, newValue);
	}

	// ɾ����ֵ��
	public void del(Integer key) {
		KV.remove(key);
		KI.remove(key);
	}

	// ���ݼ���ѯֵ
	public String find(Integer key) {
		String value = KV.get(key);
		int n = KI.get(key);
		KI.replace(key, n, n + 1);
		return value;
	}

	@SuppressWarnings("unchecked")

	// �����ݴ��ļ�����
	public void upLoad(String[] args) {
		File fileValue = new File("../../dataValue.txt");
		File fileTimes = new File("../../dataTimes.txt");
		// ��
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

	// ������д���ļ�
	public void download(String[] args) {
		File fileValue = new File("../../dataValue.txt");
		File fileTimes = new File("../../dataTimes.txt");
		// д
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

	// ��ѯ����k��ļ�ֵ��
	public String findK(int k) {
		String res, targetValue;
		int size, num, targetKey;
		size = KI.size();
		num = 0;// counter

		// key������Ͳ�ѯ����������
		int[] key = new int[size];
		int[] times = new int[size];

		// hashMap����
		for (HashMap.Entry<Integer, Integer> entry : KI.entrySet()) {
			key[num] = entry.getKey();
			times[num] = entry.getValue();
			num++;
		}

		// kֵ�Ϸ����ж�
		if (k <= size && k >= 0) {
			// �õ�times����Key
			targetKey = findKth(0, size - 1, key, times, k);
			// ����key��value
			targetValue = KV.get(targetKey);
			// ֱ�ӷ�String�ܲ��õ��Ҳ���д�����ˣ�����û�У�дʲô����,֮��һ����ע��
			res = "��ѯ����" + k + "��ļ�ֵ����" + targetKey + ":" + targetValue;
		} else {
			res = "kֵ���Ϸ��򳬹��˼�ֵ������";
		}
		return res;
	}
	
	// ����������findKth��partSort��array1��ʾkey,array2��ʾtimes
	
	// �ж�����±���û�е�k��λ��
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

	// ����˼�루���򣩣�һ�����򣬷��η�
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
