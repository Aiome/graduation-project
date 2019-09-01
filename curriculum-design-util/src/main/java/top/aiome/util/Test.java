package top.aiome.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: curriculum-design<br>
 * Description: <br>
 * Copyright: Copyright (c) 2018<br>
 * Company: 北京云杉世界信息ji术有限公司<br>
 *
 * @author mahongyan 2018/10/3 20:57
 */
public class Test {
    public static Long START = 1538877596000L;

    public static class Model implements Runnable {
        private String csrf;
        private String cookie;
        private String phone;
        public Model(String phone, String csrf,String cookie){
            this.csrf = csrf;
            this.cookie = cookie;
            this.phone = phone;
        }
        public void run(){
            System.out.println(System.currentTimeMillis() + "===============Phone:" + this.phone + "===reday");
            while (true){
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (START <= System.currentTimeMillis()){
                    for (int i = 1; i <= 25; i++) {
                        Map<String,String> param = new HashMap<>();
                        param.put("coupon_id","1024");
                        param.put("_csrf", this.csrf);

                        long start = System.currentTimeMillis();
                        Map<String, String> result = null;
                        try {
                            result = HttpBmccUtil.doPost("https://y.buslive.cn/hd/web/index.php?r=goodlive_lq%2Findex%2Fgetlqapi", param ,this.cookie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(start + " " +System.currentTimeMillis() + " " + this.phone + "  " + i + " 结果" + result.get("bmccResult"));
                    }
                    System.out.println(System.currentTimeMillis() + " " + this.phone + "===end");
                    break;
                }

            }
        }
    }

    public static void main(String[] args) {
        List<Thread> threadPool = new ArrayList<>();

        threadPool.add(new Thread(new Model(
                "1",
                "b3AuaG5xNXRYPHE/HxdHQihCVD0mFncTIEYeJSoCUC43R0w8GCh8QQ==",
                "jc1def6e=5bba0bbcb90da; PHPSESSID=38m5lgo5kfs3i45tfheqmd5524; PHPSESSID=38m5lgo5kfs3i45tfheqmd5524; _csrf=13dd771c6c6271c1ed1795dbc5f2beafedf0d3dca241d9a47597b2fe551d5427a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%227L_Wqfr6G2zUHgBgO60MDseZX7bTvYI5%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "2",
                "VDNDQXZodXYmanIiHiU4Ij1UGXEhHVg1LX8IcQQbGyAWQhAtBVwnOQ==",
                "jc1def6e=5bba0e6ca0ee7; PHPSESSID=4ipqi935rhqfi5im03inobvpb1; PHPSESSID=4ipqi935rhqfi5im03inobvpb1; _csrf=b8b4a7878d5c5fb2a2890e05a4db40554ff9f67cf877bd3c4eb30a2ab88c8327a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22rY1chMMTigZ0Wu-CyLK0rsnVBqSls4RO%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "3",
                "ZVE5Q0YxNFUmNlUoF3gMZVQfSRMzUkc.NyJKKTFoYB8hJlQEC0QHJg==",
                "jc1def6e=5bba0e919d8b7; PHPSESSID=u5hdud12qs1b3gq3t7gf2i1nt4; PHPSESSID=u5hdud12qs1b3gq3t7gf2i1nt4; _csrf=4c2d3a5c7888b6fde70e14af959a92c617ff1f49078daac502e5ecfe36e974d8a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22CglkQI801NpPucskRssjwYTJDwmGMu3s%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "4",
                "VHd2aWNzbnI8OUACVzkYX2RELFA3Kg9ABQNDHzIKGAg9FSRZGTBfPg==",
                "jc1def6e=5bba0e922a9b2; PHPSESSID=e6o68agkil5c1diksrlcatesd3; PHPSESSID=e6o68agkil5c1diksrlcatesd3; _csrf=b0b7b8458098d1f30be86a517262e14aedab7ef5469676f9f7c7ff3f57858e9fa%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22hN6k4Jv-03Z9TYa2Qt5vQyvzibR0zC1L%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "5",
                "SGZTR0ZrZzYqMhY9J18wAQwVCwIgGS8AIVdrBn4AMWUZVAsuKiQlQg==",
                "jc1def6e=5bba102745b07; PHPSESSID=6tuqisncaqrettg17b7h25pbv3; PHPSESSID=6tuqisncaqrettg17b7h25pbv3; _csrf=280c4560a5d73af1e530be289387f9478df79abaa7af132ef925965694f574e0a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22bTEza4W7DsXEfrH6i18A8kVSQ2XilOBt%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "",
                "dXUuOXVpRWMcWBxbG1F3JhwcG2gQNiNXEBlMfg1QJgYxAxl9DFARVA==",
                "jc1def6e=5bba106bccd26; PHPSESSID=imlp029d76dks6956e0cv24vk3; PHPSESSID=imlp029d76dks6956e0cv24vk3; _csrf=16411abf69b670825aabb30df89dd49c1b8150001b849f6a1cd4bfc54db089c4a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22i-2bn82Eii5Qe_f4elbGx9ceDv7Dy9T7%22%3B%7D")));
        threadPool.add(new Thread(new Model(
                "限制",
                "ZUthMDVBSVYIBRJ1YnUNZAgjJQBiBWRkVnpScmINLGccHwN5XREWBg==",
                "jc1def6e=5bba14244f964; PHPSESSID=prkrdnrukv347da3ppffpd0801; PHPSESSID=prkrdnrukv347da3ppffpd0801; _csrf=8afee7d094fa561580bf47d3a672da05e41fe50e52566a9c2954ca9b7b1302eba%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22mNsEW4D2mhD0WD-2313BWLe1yTbIhP_P%22%3B%7D")));




        for (int i = 0; i < threadPool.size(); i++) {
            threadPool.get(i).start();
        }

    }
}
