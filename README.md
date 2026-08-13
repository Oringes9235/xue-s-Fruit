# Xue's Fruit

一个 Minecraft Fabric 模组，添加 10 种水果、5 种加工食品与果酒（共 16 种食物物品），并配套果树种植系统、加工机器、独特效果系统、世界生成、村民交易与成就系统。

---

## 1. 项目结构说明

```
xues-fruit/
├── build.gradle                          # 构建脚本（Fabric Loom）
├── settings.gradle                       # Gradle 设置
├── gradle.properties                     # 版本号集中管理
├── fabric_api.json                       # Fabric API 版本信息（JSON 列表）
├── LICENSE                               # MIT 开源协议
├── gradlew / gradlew.bat                 # Gradle Wrapper 启动脚本
├── gradle/wrapper/                       # Gradle Wrapper 配置（9.2.0）
├── src/
│   ├── main/
│   │   ├── java/com/xuefengpeng/fruit/
│   │   │   ├── XuesFruitMod.java              # 主类（服务端入口）
│   │   │   ├── XuesFruitModClient.java        # 客户端入口
│   │   │   ├── item/                          # 物品系统
│   │   │   │   ├── ModItems.java              #   物品注册（16 种食物）
│   │   │   │   ├── ModFoodComponents.java     #   食物属性定义
│   │   │   │   ├── FruitItem.java             #   普通水果
│   │   │   │   ├── SpecialFruitItem.java      #   特殊水果（榴莲）
│   │   │   │   ├── FruitJuiceItem.java        #   果汁 / 果酒
│   │   │   │   └── FruitCombinationItem.java  #   组合食物
│   │   │   ├── block/                         # 方块系统
│   │   │   │   ├── ModBlocks.java             #   方块注册
│   │   │   │   ├── FruitSaplingBlock.java     #   果树苗（生长核心）
│   │   │   │   ├── FruitLeavesBlock.java      #   果树叶
│   │   │   │   ├── FruitLogBlock.java         #   果树原木
│   │   │   │   ├── JuicerBlock.java           #   榨汁机
│   │   │   │   ├── DryerBlock.java            #   烘干机
│   │   │   │   └── FermentationBarrelBlock.java # 发酵桶
│   │   │   ├── blockentity/                   # 方块实体
│   │   │   │   ├── ModBlockEntities.java      #   方块实体注册
│   │   │   │   ├── JuicerBlockEntity.java     #   榨汁机逻辑
│   │   │   │   ├── DryerBlockEntity.java      #   烘干机逻辑
│   │   │   │   └── FermentationBarrelBlockEntity.java # 发酵桶逻辑
│   │   │   ├── screen/                        # GUI
│   │   │   │   ├── ModScreenHandlers.java     #   屏幕处理器注册
│   │   │   │   ├── JuicerScreenHandler.java   #   榨汁机 GUI 逻辑
│   │   │   │   ├── JuicerScreen.java          #   榨汁机 GUI 渲染
│   │   │   │   ├── DryerScreenHandler.java    #   烘干机 GUI 逻辑
│   │   │   │   └── DryerScreen.java           #   烘干机 GUI 渲染
│   │   │   ├── effect/                        # 效果系统
│   │   │   │   ├── ModEffects.java            #   自定义效果注册
│   │   │   │   ├── FruitStatusEffect.java     #   效果基类
│   │   │   │   └── FruitEffects.java          #   水果效果工厂
│   │   │   ├── worldgen/                      # 世界生成
│   │   │   │   ├── ModWorldGen.java           #   世界生成主类
│   │   │   │   ├── FruitTreeFeature.java      #   果树生成
│   │   │   │   └── WildFruitBushFeature.java  #   野生果丛
│   │   │   ├── recipe/                        # 配方系统
│   │   │   │   ├── ModRecipes.java            #   配方注册
│   │   │   │   ├── JuicerRecipe.java          #   榨汁配方
│   │   │   │   └── DryerRecipe.java           #   烘干配方
│   │   │   ├── trade/                         # 村民交易
│   │   │   │   └── ModVillagerTrades.java     #   农民交易
│   │   │   ├── advancement/                   # 成就系统
│   │   │   │   └── ModAdvancements.java       #   成就触发
│   │   │   └── datagen/                       # 数据生成
│   │   │       ├── ModDataGenerator.java      #   DataGen 入口
│   │   │       ├── ModModelProvider.java      #   模型生成
│   │   │       ├── ModRecipeProvider.java     #   配方生成
│   │   │       ├── ModLootTableProvider.java  #   战利品表生成
│   │   │       ├── ModBlockTagProvider.java   #   方块标签生成
│   │   │       ├── ModItemTagProvider.java    #   物品标签生成
│   │   │       └── ModAdvancementProvider.java #  成就生成
│   │   └── resources/
│   │       ├── fabric.mod.json
│   │       └── assets/xuesfruit/
│   │           ├── lang/                      # 语言文件（en_us / zh_cn）
│   │           ├── models/                    # 模型 JSON
│   │           ├── textures/                  # 贴图（需手动补充 PNG）
│   │           ├── blockstates/               # 方块状态 JSON
│   │           └── ...
│   │       └── data/xuesfruit/
│   │           ├── recipes/                   # 配方 JSON
│   │           ├── loot_tables/               # 战利品表 JSON
│   │           ├── tags/                      # 标签 JSON
│   │           └── worldgen/                  # 世界生成配置
│   └── test/java/com/xuefengpeng/fruit/
│       └── XuesFruitModTest.java              # 单元测试
```

> 说明：`data/xuesfruit/advancements/` 目录由 `runDatagen` 运行后自动生成，未提交到仓库。

---

## 2. 核心功能

### 食物系统（16 种物品）

| 类别 | 物品 |
|------|------|
| 水果类（10） | 香蕉、橙子、葡萄、芒果、草莓、火龙果、榴莲、荔枝、猕猴桃、蓝莓 |
| 加工类（5） | 果汁、果酱、果干、水果沙拉、水果蛋糕 |
| 其它 | 果酒（发酵桶产物） |

### 种植系统
- 10 种果树苗，5 个生长阶段：`苗 → 幼树 → 成树 → 开花 → 结果`
- 成熟后周期掉落对应水果
- 支持骨粉催熟（实现 `Fertilizable` 接口）
- 要求光照（天空亮度 ≥ 9）与土壤（草方块 / 泥土 / 耕地）

### 加工机器
| 机器 | 功能 | GUI |
|------|------|-----|
| 榨汁机 | 水果 + 燃料 → 果汁 | 有（3 槽位） |
| 烘干机 | 水果 + 燃料 → 果干 | 有（3 槽位） |
| 发酵桶 | 水果 → 果酒（2 分钟） | 无（右键交互） |

### 效果系统
每种水果提供独特效果：

| 水果 | 效果 |
|------|------|
| 香蕉 | 生命回复 |
| 橙子 | 维生素爆发（提升攻击力与移速） |
| 葡萄 | 血糖激增（提升速度） |
| 芒果 | 饱食 |
| 草莓 | 幸运 |
| 火龙果 | 抗火 |
| 榴莲 | 力量（吃多会上火） |
| 荔枝 | 夜视 |
| 猕猴桃 | 清爽提神（提升挖掘速度） |
| 蓝莓 | 免疫强化（提升生命上限） |

- 果汁：清除负面效果
- 组合 Buff：水果沙拉 / 水果蛋糕提供多效果叠加
- 自定义效果：维生素爆发、血糖激增、清爽提神、上火、免疫强化

### 世界生成
- 每种水果果树群系生成（DataGen 配置）
- 野生水果灌木
- 村民交易（农民职业）
- 战利品表集成

### 成就系统
- 种植第一棵果树
- 品尝所有水果
- 制作终极水果沙拉
- 收集所有果树

---

## 3. 构建与测试指南

### 前置要求
- JDK 17（Minecraft 1.20.1 需要 Java 17，见 `fabric.mod.json` 的 `java: >=17`）
- 网络连接（首次构建需下载依赖）

> ⚠️ **重要**：项目已内置 Gradle Wrapper 并配置为 **Gradle 9.2.0**。
> 请始终使用 `gradlew` 命令，不要使用系统全局的旧版 `gradle` 命令。
> 首次运行 `gradlew` 会自动下载 Gradle 9.2.0 发行版。

### 常用命令

**首次构建（自动下载 Gradle 9.2.0 发行版）**
```bash
.\gradlew.bat --version   # Windows（首次会下载 Gradle 9.2.0）
```

**编译并打包**
```bash
.\gradlew.bat build
# 产物输出到 build/libs/xuesfruit-1.0.0.jar
```

**运行数据生成**
```bash
.\gradlew.bat runDatagen
```

**启动客户端测试**
```bash
.\gradlew.bat runClient
```

**运行单元测试**
```bash
.\gradlew.bat test
```

**清理构建**
```bash
.\gradlew.bat clean
```

### 资源贴图补充说明（完整命名清单）

本仓库已通过脚本生成所有 JSON 资源，但 **贴图（PNG）文件需手动补充**。
贴图必须严格按以下文件名命名，才能被模型 JSON 正确引用。
贴图缺失会导致模型渲染为紫黑色（Missing Texture），但不影响编译。

#### 1. 物品贴图（16 种食物，16×16 像素）
存放目录：`src/main/resources/assets/xuesfruit/textures/item/`

```
banana.png            # 香蕉
orange.png            # 橙子
grape.png             # 葡萄
mango.png             # 芒果
strawberry.png        # 草莓
dragon_fruit.png      # 火龙果
durian.png            # 榴莲
lychee.png            # 荔枝
kiwi.png              # 猕猴桃
blueberry.png         # 蓝莓
fruit_juice.png       # 果汁
fruit_jam.png         # 果酱
dried_fruit.png       # 果干
fruit_salad.png       # 水果沙拉
fruit_cake.png        # 水果蛋糕
fruit_wine.png        # 果酒
```

#### 2. 机器方块贴图（16×16 像素）
存放目录：`src/main/resources/assets/xuesfruit/textures/block/`

```
juicer.png                # 榨汁机（六面同贴图）
dryer.png                 # 烘干机（六面同贴图）
fermentation_barrel.png   # 发酵桶（六面同贴图）
```

#### 3. 果树方块贴图
存放目录：`src/main/resources/assets/xuesfruit/textures/block/`

每种水果需要 **4 张贴图**：原木侧面、原木顶面、树叶、树苗。
以下为 10 种水果的完整清单（共 40 张）：

| 水果 | 原木侧面 `_log.png` | 原木顶面 `_log_top.png` | 树叶 `_leaves.png` | 树苗 `_sapling.png` |
|------|--------------------|------------------------|-------------------|--------------------|
| 香蕉 | banana_log.png | banana_log_top.png | banana_leaves.png | banana_sapling.png |
| 橙子 | orange_log.png | orange_log_top.png | orange_leaves.png | orange_sapling.png |
| 葡萄 | grape_log.png | grape_log_top.png | grape_leaves.png | grape_sapling.png |
| 芒果 | mango_log.png | mango_log_top.png | mango_leaves.png | mango_sapling.png |
| 草莓 | strawberry_log.png | strawberry_log_top.png | strawberry_leaves.png | strawberry_sapling.png |
| 火龙果 | dragon_fruit_log.png | dragon_fruit_log_top.png | dragon_fruit_leaves.png | dragon_fruit_sapling.png |
| 榴莲 | durian_log.png | durian_log_top.png | durian_leaves.png | durian_sapling.png |
| 荔枝 | lychee_log.png | lychee_log_top.png | lychee_leaves.png | lychee_sapling.png |
| 猕猴桃 | kiwi_log.png | kiwi_log_top.png | kiwi_leaves.png | kiwi_sapling.png |
| 蓝莓 | blueberry_log.png | blueberry_log_top.png | blueberry_leaves.png | blueberry_sapling.png |

#### 4. GUI 贴图（256×256 像素）
存放目录：`src/main/resources/assets/xuesfruit/textures/gui/`

```
juicer.png      # 榨汁机 GUI 背景（含 3 个槽位与进度/燃料条图形区域）
dryer.png       # 烘干机 GUI 背景（同上）
```

#### 5. Mod 图标
存放目录：`src/main/resources/assets/xuesfruit/`

```
icon.png        # Mod 图标（推荐 128×128 像素）
```

#### 贴图制作提示
- 物品贴图参考原版 16×16 像素风格，缩小后会在物品栏中清晰显示。
- 树叶与树苗建议使用绿色系半透明/渐变纹理。
- GUI 背景图可复用原版熔炉/工作台贴图布局，或自定义绘制。
- 贴图完成后使用 `gradlew runClient` 进入游戏验证渲染效果。

---

## 4. 版本信息

| 项目 | 版本 |
|------|------|
| Mod | 1.0.0 |
| Minecraft | 1.20.1 |
| Fabric Loader | 0.15.11 |
| Fabric API | 0.92.2+1.20.1 |
| Yarn Mappings | 1.20.1+build.10 |
| Java | 17 |
| Gradle Wrapper | 9.2.0 |

---

## 5. 许可

MIT License。详见 [LICENSE](LICENSE) 文件。