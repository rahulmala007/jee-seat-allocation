# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='candidate',
            name='dob',
            field=models.CharField(max_length=10, default='01-01-95'),
            preserve_default=True,
        ),
    ]
