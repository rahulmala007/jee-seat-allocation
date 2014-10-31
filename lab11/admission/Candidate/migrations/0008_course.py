# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('Candidate', '0007_auto_20141029_1922'),
    ]

    operations = [
        migrations.CreateModel(
            name='course',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('code', models.CharField(max_length=5)),
                ('go', models.CharField(max_length=10)),
                ('gc', models.CharField(max_length=10)),
                ('oo', models.CharField(max_length=10)),
                ('oc', models.CharField(max_length=10)),
                ('sco', models.CharField(max_length=10)),
                ('scc', models.CharField(max_length=10)),
                ('sto', models.CharField(max_length=10)),
                ('stc', models.CharField(max_length=10)),
                ('gpo', models.CharField(max_length=10)),
                ('gpc', models.CharField(max_length=10)),
                ('opo', models.CharField(max_length=10)),
                ('opc', models.CharField(max_length=10)),
                ('scpo', models.CharField(max_length=10)),
                ('scpc', models.CharField(max_length=10)),
                ('stpo', models.CharField(max_length=10)),
                ('stpc', models.CharField(max_length=10)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
