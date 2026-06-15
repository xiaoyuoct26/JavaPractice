/**
 * Java 学习笔记：this 和 super 关键字详解
 * 日期：2026-06-15
 * 
 * 核心概念：
 *   this  → 指向【当前对象本身】，用来访问本类的属性和方法
 *   super → 指向【父类对象】，用来访问父类的属性和方法
 */

// ==================== 父类：动物 ====================
class Animal {
    // 父类的属性
    String name;
    int age;
    
    // 父类构造方法 ①：无参
    public Animal() {
        System.out.println("🔵 Animal 无参构造被调用");
    }
    
    // 父类构造方法 ②：有参
    public Animal(String name, int age) {
        this.name = name;   // ← this.name 是本类的属性，右边的 name 是参数
        this.age = age;
        System.out.println("🔵 Animal 有参构造：我叫 " + name + "，今年 " + age + " 岁");
    }
    
    // 父类的普通方法
    public void eat() {
        System.out.println("🔵 Animal.eat()：动物在吃东西");
    }
    
    public void sleep() {
        System.out.println("🔵 Animal.sleep()：" + name + " 在睡觉");
    }
}

// ==================== 子类：狗 ====================
class Dog extends Animal {
    // 子类自己的属性
    String breed;   // 品种
    
    // 子类构造方法 ①：无参 → 通过 this() 调用本类的另一个构造方法
    public Dog() {
        this("旺财", 3, "金毛");   // ← this() 调用本类另一个构造方法
        System.out.println("🟢 Dog 无参构造被调用（通过 this() 链式调用）");
    }
    
    // 子类构造方法 ②：有参 → 通过 super() 调用父类构造方法
    public Dog(String name, int age, String breed) {
        super(name, age);   // ← super() 调用父类构造方法，必须写在第一行！
        this.breed = breed; // ← this.breed 是本类的属性
        System.out.println("🟢 Dog 有参构造：品种是 " + breed);
    }
    
    // ===== 子类重写父类方法 =====
    @Override
    public void eat() {
        // 方式一：先调用父类的 eat()，再扩展自己的逻辑
        super.eat();   // ← super.method() 调用父类的方法
        System.out.println("🟢 Dog.eat()：狗在吃狗粮 🦴");
    }
    
    // 子类自己的方法
    public void bark() {
        System.out.println("🟢 Dog.bark()：" + this.name + " 汪汪叫！🐶");
        //                       ↑ this.name 其实继承自父类
        //                       ↑ 这里写 this.name 或 super.name 都可以，因为子类没有覆盖 name
    }
    
    // 演示：当子类属性覆盖了父类属性时
    String name = "子类默认名";  // ⚠️ 覆盖了父类的 name
    
    public void showNameDifference() {
        System.out.println("══════════════════════════");
        System.out.println("子类 Dog.name    (this.name)  = " + this.name);   // 子类的 name
        System.out.println("父类 Animal.name (super.name) = " + super.name);  // 父类的 name
        System.out.println("══════════════════════════");
    }
}

// ==================== 主程序 ====================
public class ThisAndSuperDemo {
    public static void main(String[] args) {
        System.out.println("========== Java this 和 super 关键字演示 ==========\n");
        
        // 1. 用有参构造创建对象
        System.out.println("【示例1】创建 Dog 对象（有参构造）");
        System.out.println("---------------------------------");
        Dog dog1 = new Dog("小白", 2, "萨摩耶");
        System.out.println();
        
        // 2. 用无参构造创建对象（内部通过 this() 链式调用）
        System.out.println("【示例2】创建 Dog 对象（无参构造 → this() 链式调用）");
        System.out.println("-------------------------------------------------");
        Dog dog2 = new Dog();
        System.out.println();
        
        // 3. 调用重写的方法（内部用 super 调了父类方法）
        System.out.println("【示例3】调用重写的 eat() 方法");
        System.out.println("-----------------------------");
        dog1.eat();
        System.out.println();
        
        // 4. 调用继承的方法
        System.out.println("【示例4】调用继承的 sleep() 方法");
        System.out.println("--------------------------------");
        dog1.sleep();
        System.out.println();
        
        // 5. 调用子类自己的方法
        System.out.println("【示例5】调用子类自己的 bark() 方法");
        System.out.println("-----------------------------------");
        dog1.bark();
        System.out.println();
        
        // 6. 演示 this.name vs super.name
        System.out.println("【示例6】当子类覆盖了父类属性时");
        System.out.println("-------------------------------");
        dog1.showNameDifference();
        
        System.out.println("\n========== 演示结束 ==========");
        System.out.println("\n📝 小结：");
        System.out.println("  this.xxx   → 访问【本类】的属性/方法");
        System.out.println("  super.xxx  → 访问【父类】的属性/方法");
        System.out.println("  this(...)  → 调用【本类】另一个构造方法");
        System.out.println("  super(...) → 调用【父类】构造方法（必须在第一行）");
    }
}
