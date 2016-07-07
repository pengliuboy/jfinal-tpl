/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : template

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-07-07 11:47:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '4');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '356a192b7913b04c54574d18c28d46e6395428ab', '1');
INSERT INTO `user` VALUES ('2', 'zs', '356a192b7913b04c54574d18c28d46e6395428ab', '1');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `per_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`per_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '3');
INSERT INTO `role_permission` VALUES ('1', '4');
INSERT INTO `role_permission` VALUES ('4', '1');
INSERT INTO `role_permission` VALUES ('4', '2');
INSERT INTO `role_permission` VALUES ('4', '3');
INSERT INTO `role_permission` VALUES ('4', '4');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `available` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '管理员', '1');
INSERT INTO `role` VALUES ('4', 'editor', '资讯编辑员', '1');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'info:add', '后台咨询添加权限', '1');
INSERT INTO `permission` VALUES ('2', 'info:del', '资源的删除权限', '1');
INSERT INTO `permission` VALUES ('3', 'info:index', '查看资讯', '1');
INSERT INTO `permission` VALUES ('4', 'info:edit', '编辑资讯', '1');

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `digest` varchar(512) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `content` text,
  `thumbnailAttaId` varchar(32) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info
-- ----------------------------
INSERT INTO `info` VALUES ('12559daa81a5449db5d6ddf28eb24003', '焦虑抑郁症状及其描述：老幼有别<sup>*</sup>', '老年患者最常担心家庭、经济及各种琐事，而年轻GAD患者则最常担心人际事务、工作、经济及家庭。与年轻个体相比，老年焦虑障碍患者对工作/学校事务更好开展工作。', '医脉通-心内科', '医脉通', '<p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif; text-indent: 2em;\">焦虑抑郁症状可随着时间的推移而发生改变；此外，老年人也常使用不同的术语描述自己的精神健康状况，这也导致针对这一群体焦虑及抑郁症状识别的不足。然而目前为止，研究者尚未针对老年及年轻患者的症状差异进行探讨。</span><br/></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\">日\r\n前，来自澳大利亚麦考瑞大学等机构的研究者针对老年及年轻样本的焦虑及抑郁症状的差异进行了探讨；此外，研究者还探讨了两个样本在描述其担忧及心境低落症\r\n状时的差异。研究结果发表于国际老年精神病学学会（IPA）旗下期刊《国际老年精神病学》（International \r\nPsychogeriatrics）。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\">研\r\n究所关注的疾病包括广泛性焦虑障碍（GAD）、社交恐怖、特定恐怖、重性抑郁（MDD）及恶劣心境。研究者首先提出假设：与年轻个体相比，导致老年个体出\r\n现焦虑反应的情境/刺激因素可能有所不同；此外，老年个体可能存在不同的MDD症状谱。为验证上述假说，研究者共纳入了107名主动寻求治疗的受试者，其\r\n中47人年龄在60-84岁之间，60%为女性，来自一项共病焦虑抑郁的随机对照试验的筛查期，原发病主要为GAD（34%）和MDD（32%）；另外\r\n60人年龄在18-44岁之间，50%为女性，均满足社交恐怖诊断标准，同时共病GAD、特定恐怖、MDD等。研究者使用焦虑障碍晤谈表及症状清单探讨了\r\n两组患者在症状严重性、症状内容及描述语言方面的差异。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><strong><span style=\"font-family: arial, helvetica, sans-serif; color: rgb(192, 0, 0);\">结果显示：</span></strong></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0);\">★&nbsp;</span>社交恐怖：老年与年轻患者最常害怕的情景相似，包括在公共场合演讲、明确表明立场、拒绝请求及参加聚会；然而，老年患者所害怕的社交情境范围更窄，精神痛苦及所受到的干扰更轻；</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>GAD：老年患者最常担心家庭、经济及各种琐事，而年轻GAD患者则最常担心人际事务、工作、经济及家庭。与年轻个体相比，老年焦虑障碍患者对工作/学校事务及人际关系问题的担忧更少，但两组在GAD行为症状，以及干扰/痛苦的严重程度方面无显著差异。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>特定恐怖：老年与年轻患者所恐惧的内容大体类似，但老年患者害怕电梯/狭窄空间的比例显著高于年轻患者；</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>MDD：老年患者报告快感缺乏的比例更高，但在报告悲伤方面与年轻患者并无显著差异；疾病所导致的干扰/痛苦方面同样无显著差异；</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>恶劣心境：两组患者无显著差异；</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>对焦虑的描述：老年患者常将焦虑描述为感到“stressed”（有压力）和“tense”（紧绷），而年轻患者常将焦虑描述为感到“anxious”（焦虑）、“worried”（担忧）及“nervous”（神经紧张）。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\"><span style=\"color: rgb(192, 0, 0); text-indent: 28px;\">★&nbsp;</span>对\r\n抑郁的描述：老年患者常使用“down”（低落）及“flat”（平淡）描述心境症状，而年轻患者常使用“down”（低落）、“unhappy”（不高\r\n兴）及“unmotivated”（缺乏动力）描述心境症状。值得注意的是，某些在一般意义上似乎与老年人关系更密切的词，包括“grumpy”（暴躁）\r\n及“irritated”（易激惹），在不同年龄组中的使用并无显著差异。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\">研究者得到结论：针对不同年龄的焦虑及抑郁患者，临床医师在评估症状时需进一步扩大范围，以免遗漏某些症状；同时，医生也应充分注意患者对症状的不同描述方式，尤其是老年个体。</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\">文\r\n献索引：Wuthrich VM et al. Differences in anxiety and depression symptoms: \r\ncomparison between older and younger clinical samples. Int \r\nPsychogeriatr. 2015 Sep;27(9):1523-32. doi: 10.1017/S1041610215000526. \r\nEpub 2015 Apr 20.</span></p><p><br/></p><p style=\"text-indent: 2em;\"><span style=\"font-family: arial, helvetica, sans-serif;\">医脉通编译，转载请注明出处。</span></p><p><br/></p>', 'a8d2db09ca114130946b66ef4230afed', '2015-08-12 09:45:32', '2015-08-12 10:00:40');
INSERT INTO `info` VALUES ('1cdc92e9e429461dbc5805d1affe6536', '[CPOS2016]赖允亮：台湾社会死亡质量的蜕变', '图文整理自台湾马偕纪念医院赖允亮教授6月18日在中国抗癌协会肿瘤心理学专业委员会（CPOS）2016年学术年会“生死教育专场”的报告。', '医脉通', '医脉通', '<p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 宋体;\">台湾的社会喜欢用这样一个寓言讲死亡：</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 宋体;\"><br style=\"margin: 0px; padding: 0px;\"/></span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">有一群人，在海边忧伤地向扬帆出海的船只告别，船影越来越小，只剩桅杆顶端还看得见，最后连桅杆也消失了，人们忧伤地低语：他走了。</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 宋体;\"><br style=\"margin: 0px; padding: 0px;\"/></span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">然而就在此刻，在遥远的某一方，另一群人正张望着海平面，他们看到了同样的桅顶，他们欢呼着：他来了。</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\">&nbsp;</p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 宋体;\">十几年前赖允亮教授的母亲过世时，他在佛罗里达街上的一张羊皮上发现了下面这首诗，内心很得安慰，他<span style=\"margin: 0px; padding: 0px; font-family: 宋体;\">知道母亲其实并没有离开，她在周围。过了几年，这首诗翻译成了日文，后来又翻译成中文。最近几年台湾的追思会和葬礼上常用这首歌《化为千风》。<span style=\"margin: 0px; padding: 0px; font-family: 宋体;\">其实人死了以后，并没有离开，所怀念的人就在你的旁边。</span></span></span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\">&nbsp;</p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">Do not stand at my grave and weep</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（请别在我的坟前哭泣）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">For I am not there, I do not sleep</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我不在那里，也未曾谁去）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am a thousand winds that blow</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是拂面的千风）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am the diamond glints on snow</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是雪中钻石般闪耀的光芒）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am the sunlight on ripened grain</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是洒落麦田的金色阳光）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am the gentle autumn‘s rain</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是沾衣将湿的温柔秋雨）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">When you awaken in the morning’s hush</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（当你在清晨的静谧中醒来）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am the swift uplifting rush</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">of quiet birds in circled flight</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是天空中盘旋的飞鸟）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am the soft stars that shine at night</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我是夜晚闪亮的柔星）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">Do not stand at my grave and cry</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（不要在我的坟前哭泣）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">I am not there, I did not die</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 楷体, 楷体_GB2312, SimKai;\">（我从未躺在那里，我，未曾死去）</span></p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial, 宋体, Helvetica, sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);\">&nbsp;</p><p style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: Arial,宋体,Helvetica,sans_serif; font-size: 14px; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: 30px; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; word-spacing: 0px; background-color: rgb(255, 255, 255);\"><span style=\"margin: 0px; padding: 0px; font-family: 宋体;\">让病人有权利选择死亡，有能力选择死亡，是很多医生的心声。台湾的安宁照护能够推广的一个基本前提，就是医疗人员的心中有关于生死的想法。</span></p>', '96bca0b4d58443d092fef78da71acca6', '2016-07-01 16:19:10', '2016-07-01 16:19:10');

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` varchar(255) NOT NULL,
  `name` varchar(128) DEFAULT NULL COMMENT '文件名',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `content_type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `module` varchar(255) DEFAULT NULL COMMENT '所属模块，如news/cases等',
  `category` varchar(255) DEFAULT NULL COMMENT '分类，如thumbnail/avatar等',
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `attachment_pk` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of attachment
-- ----------------------------
INSERT INTO `attachment` VALUES ('96bca0b4d58443d092fef78da71acca6', '20160701034906269_jpg_h160.png', '8375', 'image/png', 'info', 'thumbnail', '2016-07-01 16:17:49');
INSERT INTO `attachment` VALUES ('a8d2db09ca114130946b66ef4230afed', '20150812092939191_jpg_h120.jpg', '6767', 'image/jpeg', 'info', 'thumbnail', '2015-08-12 09:45:14');
