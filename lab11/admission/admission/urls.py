from django.conf.urls import include, url
from django.conf.urls.static import static
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.contrib import admin
from admission.views import updaterl,showcl,showrl,login,logout,homepage,updateProfile,updateit,security,securitycheck,updatecl,CourseCol,rank_based,rank_based_College_calc,rank_based_Branch_calc,CourseBra
urlpatterns = [
    # Examples:
    # url(r'^$', 'admission.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^updaterl/$',updaterl),
    url(r'^showrl/$',showrl),
    url(r'^showcl/$',showcl),
    url(r'^login/$',login),
    url(r'^logout/$',logout),
    url(r'^homepage/$',homepage),
    url(r'^updateProfile/$',updateProfile),
    url(r'^updateit/$',updateit),
    url(r'^security/$',security),
    url(r'^securitycheck/$',securitycheck),
    url(r'^updatecl/$',updatecl),
    url(r'^CourseCol/$',CourseCol),
    url(r'^rank_based/$',rank_based),
    url(r'^rank_based_College_calc/$',rank_based_College_calc),
    url(r'^rank_based_Branch_calc/$',rank_based_Branch_calc),
    url(r'^CourseBra/$',CourseBra),

]
