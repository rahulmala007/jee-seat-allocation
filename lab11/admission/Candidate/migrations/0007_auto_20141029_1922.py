# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0006_auto_20141029_1554'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='book',
            name='authors',
        ),
        migrations.DeleteModel(
            name='Author',
        ),
        migrations.RemoveField(
            model_name='book',
            name='publisher',
        ),
        migrations.DeleteModel(
            name='Book',
        ),
        migrations.DeleteModel(
            name='Publisher',
        ),
        migrations.AddField(
            model_name='candidate',
            name='answer',
            field=models.CharField(default=b'user', max_length=100),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='candidate',
            name='question',
            field=models.CharField(default=b'user', max_length=100),
            preserve_default=True,
        ),
    ]
