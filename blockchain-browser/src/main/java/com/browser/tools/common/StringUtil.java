package com.browser.tools.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.dao.entity.TblBcBlock;
import com.browser.dao.entity.TblBcTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 
	 * <p>
	 * Description:将list map数据转换为文件存储数据
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年3月16日上午11:48:02
	 * @param params
	 *            需要转换的list map
	 * @param sign
	 *            分隔符,比如|
	 * @return 以|分割的字符串的值
	 */
	public static String mapToStr(List<Map<?, ?>> params, String sign) {
		StringBuffer result = new StringBuffer();

		if (null != params && params.size() > 0) {
			for (Map<?, ?> map : params) {
				result.append(mapToStr(map, sign));
			}
		}

		return result.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:将 map数据转换为文件存储数据
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年4月8日下午3:44:08
	 * @param params
	 * @param sign
	 * @return
	 */
	public static String mapToStr(Map<?, ?> params, String sign) {
		StringBuffer result = new StringBuffer();
		int sum = 1;
		for (Map.Entry<?, ?> entry : params.entrySet()) {
			if (!"".equals(entry.getValue()) && null != entry.getValue()) {
				if (sum == params.size()) {
					result.append(entry.getValue());
				} else {
					result.append(entry.getValue()).append(sign);
				}
			} else {
				if (sum == params.size()) {
					result.append("");
				} else {
					result.append("").append(sign);
				}
			}
			sum++;
		}
		return result.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:list bean 数据转换为文件存储数据
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年3月16日下午2:16:27
	 * @param params
	 *            需要转换的list bean
	 * @param sign
	 *            分隔符,比如|
	 * @return 以|分割的字符串的值
	 * @throws Exception
	 */
	public static String beanToStr(List<?> params, String sign) throws Exception {
		StringBuffer result = new StringBuffer();

		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				result.append(beanToStr(params.get(i), sign));
			}
		}

		return result.toString();
	}

	/**
     * 数据转换为文件存储数据
	 * @param t
     * @param sign
     * @param <T>
     * @return
     * @throws Exception
	 */
	public static <T> String beanToStr(T t, String sign) throws Exception {
		StringBuffer result = new StringBuffer();

		// java 反射获取bean属性
		Field[] fields = t.getClass().getDeclaredFields();
		// 属性被声明为private的,需要将setAccessible设置为true,默认的值为false
		Field.setAccessible(fields, true);
		for (int j = 0; j < fields.length; j++) {
			if (null != fields[j].get(t)) {
				if (j == fields.length - 1) {
					result.append(fields[j].get(t));
				} else {
					result.append(fields[j].get(t)).append(sign);
				}
			} else {
				if (j == fields.length - 1) {
					result.append("");
				} else {
					result.append("").append(sign);
				}
			}
		}

		return result.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:文件名称
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年3月16日上午10:36:42
	 * @param fileflowno
	 *            文件流水号
	 * @param type
	 *            文件类型
	 * @param suffix
	 *            文件后缀
	 * @return
	 */
	public static String bulidFileName(String fileflowno, String type, String suffix) {
		StringBuffer result = new StringBuffer();
		result.append(fileflowno).append("_");
		result.append(DateUtil.parseStr(new Date(), DateUtil.C_TIMES_PATTON_DEFAULT)).append("_");
		result.append(type).append(suffix);
		return result.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:将分割的字符串转换为list map
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年3月21日下午5:36:36
	 * @param title
	 *            map key值,自定义
	 * @param content
	 *            map value值,分割获取
	 * @return
	 */
	public static List<Map<String, Object>> strToMap(String[] title, String content) {
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		if (null != title && title.length > 0) {
			if (content.contains("|")) {
				String[] cont = content.split("\\|");
				if (title.length == cont.length) {
					for (int i = 0; i < title.length; i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put(title[i], cont[i]);
						resultMap.add(map);
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 该方法只返回一条数据
	 * <p>
	 * Description:将分割的字符串分割成list bean
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年3月22日上午10:19:10
	 * @param t
	 *            pojo 实体类
	 * @param content
	 *            分割的字符串
	 * @return List bean 字符串的值要对应bean属性的顺序
	 * @throws Exception
	 */
	public static <T> List<T> strTobean(T t, String content) throws Exception {
		List<T> resultList = new ArrayList<T>();
		if (content.contains("|")) {
			String[] cont = content.split("\\|");
			for (int i = 0; i < cont.length; i++) {
				Field[] fields = t.getClass().getDeclaredFields();
				Field.setAccessible(fields, true);
				fields[i].set(t, cont[i]);
			}
			resultList.add(t);
		}
		return resultList;
	}

	/**
	 * 
	 * <p>
	 * Description:修改分割字符串中的值
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年4月22日下午5:53:11
	 * @param spl
	 *            需要修改的分割字符串
	 * @param sum
	 *            需要修改的位置
	 * @param value
	 *            修改位置对应的值
	 * @return
	 */
	public static String updToString(String spl, int sum, String value) {
		String[] ct = spl.split("\\|");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ct.length; i++) {
			if (i == sum) {
				ct[i] = value;
				sb.append(ct[i]).append("|");
			} else {
				sb.append(ct[i]).append("|");
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:获取2个数的百分比
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年7月14日 下午6:22:36
	 * @param num
	 * @param sum
	 * @return
	 */
	public static String getRatiso(int num, int sum) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format((float) num / (float) sum * 100);
		return result + "%";
	}


	/**
	 * 
	 * <p>
	 * Description:去除表情包数据
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月6日 下午3:16:04
	 * @param source
	 * @return
	 */
	public static String creatBrow(String source) {
		if (source != null && source.length() > 0) {
			return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
		} else {
			return source;
		}
	}

	/**
	 * 
	 * <p>
	 * Description:获取时间戳
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月18日 下午4:33:02
	 * @return
	 */
	public static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 
	 * <p>
	 * Description:生成随机字符串
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月18日 下午5:11:32
	 * @param length
	 *            字符串的长度
	 * @return
	 */
	public static String getNonceStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFHIJKLMNOPSUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:UUID创建随机字符串
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月18日 下午5:33:49
	 * @return
	 */
	public static String getUUIDStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 
	 * <p>
	 * Description:将josn字符串转换为bean
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月18日 下午5:33:49
	 * @return
	 */
	public static TblBcBlock getBlockInfo(String message, String signess) {
		TblBcBlock block = new TblBcBlock();
		JSONObject bc = (JSONObject) JSONObject.parse(message);
		block.setBlockId(bc.getString("id"));
		block.setBlockNum(bc.getLong("block_num"));
		block.setBlockSize(bc.getLong("block_size"));
		block.setCreatedAt(new Date());
		block.setNextSecretHash(bc.getString("next_secret_hash"));
		block.setPrevious(bc.getString("previous"));
		block.setPrevSecret(bc.getString("previous_secret"));
		block.setRandomSeed(bc.getString("random_seed"));
		block.setSignee(signess);

		Date date = DateUtil.parseDate(bc.getString("timestamp"), "yyyy-MM-dd'T'HH:mm:ss");
		//utc时间转北京时间
		block.setBlockTime(date);
		block.setTrxDigest(bc.getString("transaction_digest"));
		return block;
	}
	
	/**
	 * 
	 * <p>
	 * Description:将josn字符串转换交易数据
	 * </p>
	 * 
	 * @author wanghaitao01@new4g.cn
	 * @date 2016年9月18日 下午5:33:49
	 * @return
	 */
	public static TblBcTransaction getBlockTrx(JSONArray jsa) {
		TblBcTransaction trans = new TblBcTransaction();
		if(jsa!=null && jsa.size()>1){
			//交易id
			trans.setTrxId(jsa.getString(0));
			JSONObject trx = jsa.getJSONObject(1).getJSONObject("trx");
			JSONArray operations = trx.getJSONArray("operations");

			if(operations!=null && operations.size()>0){
				String balanceId = "";
				Long withdrawAmount = 0L;
				Long depositAmount = 0L;
				String toAddress = "";
				String memo = "";
				//遍历operations获取参数
				for(int i=0;i<operations.size();i++){
					JSONObject op = operations.getJSONObject(i);
					if("withdraw_op_type".equals(op.getString("type"))){
						balanceId = op.getJSONObject("data").getString("balance_id");
						trans.setBalanceId(balanceId);
						withdrawAmount = withdrawAmount + op.getJSONObject("data").getLong("amount");
					}
					if("deposit_op_type".equals(op.getString("type"))){
						depositAmount = depositAmount + op.getJSONObject("data").getLong("amount") ;
						JSONObject conditionJson = op.getJSONObject("data").getJSONObject("condition");
						if("withdraw_multisig_type".equals(conditionJson.getString("type"))){
						}else{
							toAddress = conditionJson.getJSONObject("data").getString("owner");
						}
				}
					if("imessage_memo_op_type".equals(op.getString("type"))){
						memo = op.getJSONObject("data").getString("imessage");
					}
				}
				//转入地址
				trans.setToAccount(toAddress);
				//到账金额
				trans.setAmount(depositAmount);
				//手续费
				trans.setFee(withdrawAmount-depositAmount);
				//memo参数
				trans.setMemo(memo);
			}
		}
		return trans;
	}
	
	
	public static String fiveFee(Double fee){
		DecimalFormat df  = new DecimalFormat("###.00000");
		return df.format(fee);
	}

	public static BigDecimal div(BigDecimal b1, BigDecimal b2,int index){
		return b1.divide(b2,index, RoundingMode.HALF_EVEN);
	}

	public static String getBlockchainBalance(String message){
		JSONObject balance = JSONObject.parseObject(message);
		String fromAddress = null;
		if(balance!=null){
			fromAddress = balance.getJSONObject("condition").getJSONObject("data").getString("owner");
		}
		return fromAddress;
	}

	public static void main(String[] args){
		System.out.println(div(new BigDecimal(0),new BigDecimal(100000),5));
	}
}
