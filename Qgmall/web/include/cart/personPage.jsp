<%--
  Created by IntelliJ IDEA.
  User: yc
  Date: 2023/4/26
  Time: 1:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<div class="reviewDiv">
    <aside class="sidebar">

        <div class="sidebar-inner sidebar-overview-active animated fadeInUp">
            <ul class="sidebar-nav">
                <li class="sidebar-nav-toc">
                    文章目录
                </li>
                <li class="sidebar-nav-overview">
                    站点概览
                </li>
            </ul>

            <div class="sidebar-panel-container">
                <!--noindex-->
                <div class="post-toc-wrap sidebar-panel">
                </div>
                <!--/noindex-->

                <div class="site-overview-wrap sidebar-panel">
                    <div class="site-author animated" itemprop="author" itemscope="" itemtype="http://schema.org/Person">
                        <img class="site-author-image" itemprop="image" alt="Flog and boat" src="https://s3.bmp.ovh/imgs/2023/03/11/103be36d036cff61.png">
                        <p class="site-author-name" itemprop="name">Flog and boat</p>
                        <div class="site-description" itemprop="description"></div>
                    </div>
                    <div class="site-state-wrap animated">
                        <nav class="site-state">
                            <div class="site-state-item site-state-posts">
                                <a href="/archives/">
                                    <span class="site-state-item-count">13</span>
                                    <span class="site-state-item-name">日志</span>
                                </a>
                            </div>
                            <div class="site-state-item site-state-categories">
                                <a href="/categories/">
                                    <span class="site-state-item-count">7</span>
                                    <span class="site-state-item-name">分类</span></a>
                            </div>
                            <div class="site-state-item site-state-tags">
                                <a href="/tags/">
                                    <span class="site-state-item-count">9</span>
                                    <span class="site-state-item-name">标签</span></a>
                            </div>
                        </nav>
                    </div>
                    <div class="links-of-author animated">
      <span class="links-of-author-item">
        <a href="https://github.com/Crazylychee" title="GitHub → https://github.com/Crazylychee" rel="noopener me" target="_blank"><i class="fab fa-github fa-fw"></i>GitHub</a>
      </span>
                    </div>

                </div>
            </div>
        </div>


        <div class="sidebar-inner sidebar-blogroll animated fadeInUp">
            <div class="links-of-blogroll animated">
                <div class="links-of-blogroll-title"><i class="fa fa-globe fa-fw"></i>
                    链接
                </div>
                <ul class="links-of-blogroll-list">
                    <li class="links-of-blogroll-item">
                        <a href="https://github.com/" title="https://github.com" rel="noopener" target="_blank">Title</a>
                    </li>
                </ul>
            </div>
        </div>
    </aside>



</div>