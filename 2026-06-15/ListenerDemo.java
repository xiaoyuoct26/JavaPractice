/**
 * Java 学习笔记：监听器（Listener）模式详解
 * 日期：2026-06-15
 * 
 * 核心概念：
 *   监听器就像生活中的"保安盯监控"
 *   → 监控画面 = 事件源（被盯的东西）
 *   → 保安 = 监听器（事件发生时要做什么）
 *   → 有人闯入 = 事件（发生了什么）
 * 
 * 三个角色：
 *   ① 事件（Event）        — 发生了什么
 *   ② 事件源（EventSource） — 谁发生了事情
 *   ③ 监听器（Listener）    — 谁来处理这个事情
 */

// ==================== ① 事件类：描述"发生了什么" ====================
class DoorEvent {
    String action;   // 动作：敲门 / 开门 / 关门
    String who;      // 谁干的
    long time;       // 什么时候
    
    public DoorEvent(String action, String who) {
        this.action = action;
        this.who = who;
        this.time = System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "【事件】" + who + " " + action + "了！";
    }
}

// ==================== ② 监听器接口：规定"监听者要会什么" ====================
interface DoorListener {
    // 当有人敲门时调用
    void onKnock(DoorEvent event);
    
    // 当有人开门时调用
    void onOpen(DoorEvent event);
}

// ==================== ③ 事件源：被监听的门 ====================
class Door {
    // 注册在这个门上的监听器列表
    private DoorListener[] listeners = new DoorListener[10];
    private int listenerCount = 0;
    
    // 注册监听器 → 相当于"保安来值班了"
    public void addListener(DoorListener listener) {
        listeners[listenerCount] = listener;
        listenerCount++;
        System.out.println("✅ 监听器已注册（现在有 " + listenerCount + " 个监听器盯着这扇门）");
    }
    
    // 触发敲门事件 → 通知所有监听器
    public void knock(String who) {
        DoorEvent event = new DoorEvent("敲门", who);
        System.out.println("\n🚪 " + who + " 敲了门！");
        // 挨个通知所有监听器
        for (int i = 0; i < listenerCount; i++) {
            listeners[i].onKnock(event);
        }
    }
    
    // 触发开门事件 → 通知所有监听器
    public void open(String who) {
        DoorEvent event = new DoorEvent("开门", who);
        System.out.println("\n🚪 " + who + " 打开了门！");
        // 挨个通知所有监听器
        for (int i = 0; i < listenerCount; i++) {
            listeners[i].onOpen(event);
        }
    }
}

// ==================== ④ 具体的监听器实现 ====================

// 监听器A：门铃 → 有人敲门就响
class DoorbellListener implements DoorListener {
    String name;
    
    public DoorbellListener(String name) {
        this.name = name;
    }
    
    @Override
    public void onKnock(DoorEvent event) {
        System.out.println("  🔔 [" + name + "] 叮咚叮咚！" + event.who + " 来啦！");
    }
    
    @Override
    public void onOpen(DoorEvent event) {
        System.out.println("  🔔 [" + name + "] 门开了，" + event.who + " 进来了。");
    }
}

// 监听器B：监控摄像头 → 记录日志
class CameraListener implements DoorListener {
    @Override
    public void onKnock(DoorEvent event) {
        System.out.println("  📷 [摄像头] 录像中… 检测到 " + event.who + " 在敲门");
    }
    
    @Override
    public void onOpen(DoorEvent event) {
        System.out.println("  📷 [摄像头] 截图保存！" + event.who + " 进入了房间");
    }
}

// 监听器C：保安 → 有人来就警觉
class GuardListener implements DoorListener {
    @Override
    public void onKnock(DoorEvent event) {
        System.out.println("  💂 [保安] 注意！有人敲门，我去看看是谁");
    }
    
    @Override
    public void onOpen(DoorEvent event) {
        System.out.println("  💂 [保安] " + event.who + " 进来了，保持警惕");
    }
}


// ==================== 主程序 ====================
public class ListenerDemo {
    public static void main(String[] args) {
        System.out.println("========== Java 监听器模式演示 ==========\n");
        
        // 1. 创建一个门（事件源）
        Door door = new Door();
        
        // 2. 给门装上各种监听器
        door.addListener(new DoorbellListener("智能门铃"));
        door.addListener(new CameraListener());
        door.addListener(new GuardListener());
        
        System.out.println("\n--- 场景开始 ---\n");
        
        // 3. 模拟事件发生
        door.knock("小明");      // 有人敲门 → 三个监听器同时响应
        
        try { Thread.sleep(500); } catch (Exception e) {}
        
        door.open("小明");      // 有人开门 → 三个监听器同时响应
        
        try { Thread.sleep(500); } catch (Exception e) {}
        
        door.knock("快递员");   // 又有人敲门
        
        System.out.println("\n========== 演示结束 ==========");
        System.out.println("\n📝 用你自己的话说：");
        System.out.println("  门口装了多少东西不重要，重要的是：");
        System.out.println("  一敲门 → 门铃响、摄像头录、保安看");
        System.out.println("  这就是「监听器模式」—— 一件事发生，多方自动响应");
        System.out.println("\n📝 三个关键步骤：");
        System.out.println("  1. 定义接口（规定监听器要会什么）");
        System.out.println("  2. 事件源里维护监听器列表，事件发生时遍历通知");
        System.out.println("  3. 实现接口，写你自己的处理逻辑");
    }
}
