# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0008_course'),
    ]

    operations = [
        migrations.AddField(
            model_name='course',
            name='branch_name',
            field=models.CharField(default=b'', max_length=100),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='course',
            name='college_name',
            field=models.CharField(default=b'', max_length=100),
            preserve_default=True,
        ),
    ]
